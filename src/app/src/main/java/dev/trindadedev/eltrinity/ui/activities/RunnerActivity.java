package dev.trindadedev.eltrinity.ui.activities;

/*
 * Copyright 2025 Aquiles Trindade (trindadedev).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import bsh.EvalError;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import dev.trindadedev.eltrinity.databinding.ActivityRunnerBinding;
import dev.trindadedev.eltrinity.project.api.BaseAPIActivity;
import dev.trindadedev.eltrinity.project.ELTrinityInterpreter;
import java.io.File;

public class RunnerActivity extends BaseAPIActivity {

  @NonNull
  private ActivityRunnerBinding binding;

  @NonNull
  private ELTrinityInterpreter interpreter;

  @Override
  @NonNull
  protected ELTrinityInterpreter getInterpreter() {
    if (interpreter == null) {
      try {
        interpreter = new ELTrinityInterpreter(this);
      } catch (EvalError exc) {
        showErrorDialog(exc.toString());
      }
    }
    return interpreter;
  }

  // needed to add views at console screen
  @Override
  @NonNull
  public ViewGroup getRootViewForApi() {
    return binding.content;
  }

  @Override
  @NonNull
  protected View bindLayout() {
    binding = ActivityRunnerBinding.inflate(getLayoutInflater());
    return binding.getRoot();
  }

  @Override
  protected void onBindLayout(@Nullable final Bundle savedInstanceState) {
    super.onBindLayout(savedInstanceState);
    try {
      final String projectName = getIntent().getStringExtra("project_name");
      final File projectPath = new File(ELTrinityInterpreter.PROJECTS_PATH, projectName);

      final ELTrinityInterpreter.InterpreterEvents interpreterEvents =
          new ELTrinityInterpreter.InterpreterEvents();

      interpreterEvents.setOnLogAdded(this::updateLogsUI);

      interpreter.setProjectPath(projectPath);
      interpreter.setEvents(interpreterEvents);
      interpreter.runProjectMain();
      interpreter.getProjectLifecycleEvents().onCreate.onCallEvent();

    } catch (final EvalError e) {
      showErrorDialog("Error: " + e.getMessage());
    }
  }

  private final void updateLogsUI() {
    if (interpreter == null) return;

    binding.logsContent.removeAllViews();
    interpreter
        .getLogs()
        .forEach(
            log -> {
              final TextView textView = new TextView(this);
              textView.setText(log);
              textView.setTextSize(16);
              textView.setMaxLines(1);
              textView.setEllipsize(TextUtils.TruncateAt.END);
              binding.logsContent.addView(textView);
            });
  }

  private final void showErrorDialog(final String message) {
    final TextView textView = new TextView(this);
    textView.setText(message);
    textView.setTextIsSelectable(true);
    textView.setPadding(50, 50, 50, 50);
    textView.setMovementMethod(new ScrollingMovementMethod());

    new MaterialAlertDialogBuilder(this)
        .setTitle("Error")
        .setView(textView)
        .setPositiveButton(
            "Copy",
            (dialog, which) -> {
              final ClipboardManager clipboard =
                  (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
              final ClipData clip = ClipData.newPlainText("Copied", message);
              clipboard.setPrimaryClip(clip);
              Toast.makeText(this, "Copied!", Toast.LENGTH_SHORT).show();
            })
        .setNegativeButton("Close", null)
        .show();
  }
}

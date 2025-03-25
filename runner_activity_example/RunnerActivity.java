package dev.trindadedev.bshrunner;

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

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import bsh.EvalError;
import dev.trindadedev.bshrunner.databinding.RunnerBinding;
import dev.trindadedev.tbsh.app.TBSHActivity;
import dev.trindadedev.tbsh.project.TBSHInterpreter;
import java.io.File;

public class RunnerActivity extends TBSHActivity {

  private RunnerBinding binding;
  private TBSHInterpreter interpreter;
  
  @Override
  @NonNull
  protected TBSHInterpreter getInterpreter() {
    
    if (interpreter == null) {
      try {
        interpreter = new TBSHInterpreter(this);
      } catch (EvalError exc) {
        showErrorDialog(exc.toString());
      }
    }
    return interpreter;
  }

  @Override
  @NonNull
  public ViewGroup getRootViewForApi() {
    return binding.content;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = RunnerBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    try {
      final String projectName = getIntent().getStringExtra("project_name");
      final File projectPath = new File(TBSHInterpreter.PROJECTS_PATH, projectName);

      final TBSHInterpreter.InterpreterEvents interpreterEvents =
          new TBSHInterpreter.InterpreterEvents();

      interpreterEvents.setOnLogAdded(this::updateLogsUI);

      interpreter.setProjectPath(projectPath);
      interpreter.setEvents(interpreterEvents);
      interpreter.runProjectMain();
      interpreter.getProjectLifecycleEvents().onCreate.onCallEvent();

    } catch (EvalError e) {
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

  private final void showErrorDialog(String message) {
    final TextView textView = new TextView(this);
    textView.setText(message);
    textView.setTextIsSelectable(true);
    textView.setPadding(50, 50, 50, 50);
    textView.setMovementMethod(new ScrollingMovementMethod());

    new AlertDialog.Builder(this)
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

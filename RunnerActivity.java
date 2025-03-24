package dev.trindadedev.bshrunner;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import bsh.EvalError;
import dev.trindadedev.bshrunner.databinding.RunnerBinding;
import dev.trindadedev.bshrunner.program.ProgramInterpreter;

public class RunnerActivity extends AppCompatActivity {

  private RunnerBinding binding;
  private ProgramInterpreter interpreter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = RunnerBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    try {
      final String programName = getIntent().getStringExtra("program_name");

      if (programName == null) throw new EvalError("Program name is missing in intent extras.");

      final File programPath = new File(ProgramInterpreter.PROGRAMS_PATH, programName);
      final ProgramInterpreter.InterpreterEvents interpreterEvents = new ProgramInterpreter.InterpreterEvents();

      interpreterEvents.setOnLogAdded(this::updateLogsUI);

      interpreter = new ProgramInterpreter(this, programPath, interpreterEvents);
      interpreter.runProgramMain();
      interpreter.getProgramLifecycleEvents().onCreate.onCallEvent();

    } catch (EvalError e) {
      showSelectableDialog("Error: " + e.getMessage());
    }
  }

  private void updateLogsUI() {
    if (interpreter == null) return;

    binding.logsContent.removeAllViews();
    interpreter.getLogs().forEach(log -> {
      final TextView textView = new TextView(this);
      textView.setText(log);
      textView.setTextSize(16);
      textView.setMaxLines(1);
      textView.setEllipsize(TextUtils.TruncateAt.END);
      binding.logsContent.addView(textView);
    });
  }

  private void showSelectableDialog(String message) {
    final TextView textView = new TextView(this);
    textView.setText(message);
    textView.setTextIsSelectable(true);
    textView.setPadding(50, 50, 50, 50);
    textView.setMovementMethod(new ScrollingMovementMethod());

    new AlertDialog.Builder(this)
        .setTitle("Error")
        .setView(textView)
        .setPositiveButton("Copy", (dialog, which) -> {
          final ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
          final ClipData clip = ClipData.newPlainText("Copied", message);
          clipboard.setPrimaryClip(clip);
          Toast.makeText(this, "Copied!", Toast.LENGTH_SHORT).show();
        })
        .setNegativeButton("Close", null)
        .show();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (interpreter != null) interpreter.getProgramLifecycleEvents().onDestroy.onCallEvent();
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (interpreter != null) interpreter.getProgramLifecycleEvents().onResume.onCallEvent();
  }

  @Override
  protected void onStart() {
    super.onStart();
    if (interpreter != null) interpreter.getProgramLifecycleEvents().onStart.onCallEvent();
  }

  @Override
  protected void onPause() {
    super.onPause();
    if (interpreter != null) interpreter.getProgramLifecycleEvents().onPause.onCallEvent();
  }

  @Override
  protected void onStop() {
    super.onStop();
    if (interpreter != null) interpreter.getProgramLifecycleEvents().onStop.onCallEvent();
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    if (interpreter != null) interpreter.getProgramLifecycleEvents().onPostCreate.onCallEvent();
  }
}
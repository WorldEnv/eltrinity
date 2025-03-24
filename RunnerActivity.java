package dev.trindadedev.bshrunner;

import android.animation.*;
import android.app.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.os.*;
import android.text.*;
import android.text.method.ScrollingMovementMethod;
import android.text.style.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.view.animation.*;
import android.webkit.*;
import android.widget.*;
import androidx.activity.*;
import androidx.annotation.*;
import androidx.appcompat.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.resources.*;
import androidx.core.*;
import androidx.core.ktx.*;
import androidx.emoji2.*;
import androidx.emoji2.viewsintegration.*;
import androidx.lifecycle.livedata.core.*;
import androidx.lifecycle.process.*;
import androidx.lifecycle.runtime.*;
import androidx.lifecycle.viewmodel.*;
import androidx.lifecycle.viewmodel.savedstate.*;
import androidx.profileinstaller.*;
import androidx.savedstate.*;
import androidx.startup.*;
import androidx.transition.*;
import bsh.*;
import com.badlogic.gdx.*;
import com.google.android.material.*;
import dev.trindadedev.bshrunner.databinding.*;
import dev.trindadedev.bshrunner.program.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.json.*;

public class RunnerActivity extends AppCompatActivity {

  // ayo bad pratice lmao
  public RunnerBinding binding;
  private ProgramInterpreter interpreter = null;

  @Override
  protected void onCreate(Bundle _savedInstanceState) {
    super.onCreate(_savedInstanceState);
    binding = RunnerBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    initialize(_savedInstanceState);
    initializeLogic();
  }

  private void initialize(Bundle _savedInstanceState) {
    setSupportActionBar(binding.Toolbar);

    binding.Toolbar.setNavigationOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View _v) {
            onBackPressed();
          }
        });
  }

  private void initializeLogic() {
    setTitle(getString(R.string.bsh_runner_console));
    try {
      final var programPath =
          new File(ProgramInterpreter.PROGRAMS_PATH, getIntent().getStringExtra("program_name"));
      final var interpreterEvents = new ProgramInterpreter.InterpreterEvents();
      // maybe it's not a very efficient way, but idc
      interpreterEvents.setOnLogAdded(
          () -> {
            if (interpreter != null) {
              binding.logsContent.removeAllViews();
              interpreter
                  .getLogs()
                  .forEach(
                      log -> {
                        final var text = new TextView(this);
                        text.setText(log);
                        text.setTextSize(16);
                        text.setMaxLines(1);
                        text.setEllipsize(TextUtils.TruncateAt.END);
                        binding.logsContent.addView(text);
                      });
            }
          });

      interpreter = new ProgramInterpreter(this, programPath, interpreterEvents);
      interpreter.runProgramMain();
      interpreter.getProgramLifecycleEvents().onCreate.onCallEvent();
    } catch (final EvalError exc) {
      showSelectableDialog(exc.toString());
    }
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    interpreter.getProgramLifecycleEvents().onDestroy.onCallEvent();
  }

  @Override
  public void onResume() {
    super.onResume();
    interpreter.getProgramLifecycleEvents().onResume.onCallEvent();
  }

  @Override
  public void onStart() {
    super.onStart();
    interpreter.getProgramLifecycleEvents().onStart.onCallEvent();
  }

  @Override
  public void onPause() {
    super.onPause();
    interpreter.getProgramLifecycleEvents().onPause.onCallEvent();
  }

  @Override
  public void onStop() {
    super.onStop();
    interpreter.getProgramLifecycleEvents().onStop.onCallEvent();
  }

  @Override
  protected void onPostCreate(Bundle _savedInstanceState) {
    super.onPostCreate(_savedInstanceState);
    interpreter.getProgramLifecycleEvents().onPostCreate.onCallEvent();
  }

  public void _u() {}

  public void showSelectableDialog(String message) {

    final var textView = new TextView(this);
    textView.setText(message);
    textView.setTextIsSelectable(true);
    textView.setPadding(50, 50, 50, 50);
    textView.setMovementMethod(new ScrollingMovementMethod());

    new AlertDialog.Builder(this)
        .setTitle("Error")
        .setView(textView)
        .setPositiveButton(
            "Copiar",
            (dialog, which) -> {
              final var clipboard =
                  (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
              final var clip = (ClipData) ClipData.newPlainText("Text copied", message);
              clipboard.setPrimaryClip(clip);
              Toast.makeText(this, "Text Copied!", Toast.LENGTH_SHORT).show();
            })
        .setNegativeButton("Fechar", null)
        .show();
  }
}

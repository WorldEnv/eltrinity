package dev.trindadedev.eltrinity.project.api;

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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;
import dev.trindadedev.eltrinity.project.ELTrinityInterpreter;
import dev.trindadedev.eltrinity.project.Event;
import dev.trindadedev.eltrinity.project.Events;

public class API {

  public LifecycleEvents lifecycleEvents;

  private ELTrinityInterpreter interpreter;
  private Context context;

  public API(final Context context) {
    this(context, null);
  }

  public API(final Context context, final ELTrinityInterpreter interpreter) {
    this(context, interpreter, new LifecycleEvents());
  }

  public API(
      final Context context,
      final ELTrinityInterpreter interpreter,
      final LifecycleEvents lifecycleEvents) {

    this.context = context;
    this.interpreter = interpreter;
    this.lifecycleEvents = lifecycleEvents;
  }

  public BaseAPIActivity contextAsBaseAPIActivity() {
    if (context instanceof BaseAPIActivity) {
      return (BaseAPIActivity) context;
    }
    throw new IllegalStateException("API Must be instantiated by BaseAPIActivity.");
  }

  public void addViewAtRoot(final View view) {
    final ViewGroup rootView = contextAsBaseAPIActivity().getRootViewForApi();
    if (rootView == null) {
      addErrorLog("Failed to add view: Root view of BaseAPIActivity is not initialized.");
      return;
    }
    rootView.addView(view);
  }

  public void showToast(final String message) {
    Toast.makeText(context, message, 4000).show();
  }

  public void addTaskLog(final String log) {
    if (interpreter != null) interpreter.addTaskLog(log);
  }

  public void addSuccessLog(final String log) {
    if (interpreter != null) interpreter.addSuccessLog(log);
  }

  public void addWarningLog(final String log) {
    if (interpreter != null) interpreter.addWarningLog(log);
  }

  public void addErrorLog(final String log) {
    if (interpreter != null) interpreter.addErrorLog(log);
  }

  public void addInfoLog(final String log) {
    if (interpreter != null) interpreter.addInfoLog(log);
  }

  public WindowManager getWindowManager() {
    return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
  }

  public LayoutInflater getLayoutInflater() {
    return (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  public final void onUiThread(final Event event) {
    if (interpreter != null) interpreter.onUiThread(event);
  }
}

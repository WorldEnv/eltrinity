package dev.trindadedev.tbsh.project;

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
import dev.trindadedev.tbsh.app.TBSHActivity;
import dev.trindadedev.tbsh.event.Event;
import dev.trindadedev.tbsh.event.Events;

public class API {

  public Project project;
  public LifecycleEvents lifecycleEvents;

  private TBSHInterpreter interpreter;
  private Context context;

  public API(final Context context) {
    this.context = context;
    this.project = new Project();
    this.lifecycleEvents = new LifecycleEvents();
  }

  public API(final Context context, final TBSHInterpreter interpreter) {
    this(context);
    this.interpreter = interpreter;
  }

  public TBSHActivity contextAsTBSHActivity() {
    if (context instanceof TBSHActivity) {
      return (TBSHActivity) context;
    }
    throw new IllegalStateException("API Must be instantiated by TBSHActivity.");
  }

  public void addViewAtRoot(final View view) {
    final ViewGroup rootView = contextAsTBSHActivity().getRootViewForApi();
    if (rootView == null) {
      addErrorLog("Failed to add view: Root view of TBSHActivity is not initialized.");
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

  public static class LifecycleEvents extends Events {
    public Event onCreate = emptyEvent();
    public Event onResume = emptyEvent();
    public Event onDestroy = emptyEvent();
    public Event onStart = emptyEvent();
    public Event onPause = emptyEvent();
    public Event onStop = emptyEvent();
    public Event onPostCreate = emptyEvent();

    public void setOnCreate(final Event onCreate) {
      this.onCreate = onCreate;
    }

    public void setOnResume(final Event onResume) {
      this.onResume = onResume;
    }

    public void setOnDestroy(final Event onDestroy) {
      this.onDestroy = onDestroy;
    }

    public void setOnStart(final Event onStart) {
      this.onStart = onStart;
    }

    public void setOnPause(final Event onPause) {
      this.onPause = onPause;
    }

    public void setOnStop(final Event onStop) {
      this.onStop = onStop;
    }

    public void setOnPostCreate(final Event onPostCreate) {
      this.onStart = onStart;
    }
  }

  public enum Version {
    One;
  }
}

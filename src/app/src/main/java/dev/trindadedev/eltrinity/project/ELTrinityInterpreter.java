package dev.trindadedev.eltrinity.project;

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
import bsh.EvalError;
import bsh.Interpreter;
import dev.trindadedev.eltrinity.utils.FileUtil;
import dev.trindadedev.eltrinity.project.api.API;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ELTrinityInterpreter extends Interpreter {

  private static final String LOG_TASK = "[TASK]";
  private static final String LOG_SUCCESS = "[SUCCESS]";
  private static final String LOG_WARNING = "[WARNING]";
  private static final String LOG_ERROR = "[ERROR]";
  private static final String LOG_INFO = "[INFO]";

  public InterpreterEvents events;

  protected Context context;
  protected File projectPath;
  protected List<String> logs;
  protected API api;

  public ELTrinityInterpreter(final Context context) throws EvalError {
    super();
    this.context = context;
    this.logs = new ArrayList<>();
    this.events = new InterpreterEvents();
    this.api = new API(context, this);
    configureVariables();
  }

  public ELTrinityInterpreter(final Context context, final File projectPath) throws EvalError {
    this(context, projectPath, new InterpreterEvents());
  }

  public ELTrinityInterpreter(final Context context, final File projectPath, InterpreterEvents events)
      throws EvalError {
    this(context);
    setProjectPath(projectPath);
    setEvents(events);
  }

  public void setProjectPath(File projectPath) {
    this.projectPath = projectPath;
  }

  public void setEvents(InterpreterEvents events) {
    this.events = events;
  }

  private void configureVariables() throws EvalError {
    set("projectPath", projectPath);
    set("context", context);
    set("api", api);
    addTaskLog("Environment variables defined.");
  }

  /** Compiles the main file of Project. */
  public void runProjectMain() throws EvalError {
    if (projectPath == null) {
      addErrorLog("Project path is not set!");
      return;
    }

    if (!getProjectMainFile().exists()) {
      addErrorLog("Project main.bsh File Not Exists!\n");
      return;
    }

    final String projectMainContent = FileUtil.readFile(getProjectMainFile());

    if (projectMainContent.isEmpty()) {
      addErrorLog("Empty Project main.bsh File!\n");
      return;
    }
    eval(projectMainContent);

    addSuccessLog("Compiled successfully!");

    if (api.project.getName() == null || api.project.getName().isEmpty()) {
      addWarningLog("Please provide Project Name");
    } else {
      addInfoLog("Running " + api.project.getName() + "...");
    }

    if (api.project.getDescription() == null || api.project.getDescription().isEmpty()) {
      addWarningLog("Please provide Project Description");
    } else {
      addInfoLog("Project Description: " + api.project.getDescription());
    }

    if (api.project.getApiVersion() == null) {
      addWarningLog("Please declare the API version of your project");
    } else {
      addInfoLog("Project API Version: " + api.project.getApiVersion().toString());
    }

    final String authorName = api.project.getAuthorName();
    final String authorUserName = api.project.getAuthorUserName();

    if ((authorName == null || authorName.isEmpty())
        && (authorUserName == null || authorUserName.isEmpty())) {
      addWarningLog("Please provide Author Info");
    } else {
      addInfoLog(
          "Project Author: "
              + (authorName != null ? authorName : "N/A")
              + " ("
              + (authorUserName != null ? authorUserName : "N/A")
              + ")");
    }
  }

  /** Returns the main file of Project. */
  public File getProjectMainFile() {
    return new File(projectPath, "main.bsh");
  }

  public List<String> getLogs() {
    return logs;
  }

  public void addTaskLog(final String log) {
    logs.add(LOG_TASK + ": " + log);
    events.onLogAdded.onCallEvent();
  }

  public void addSuccessLog(final String log) {
    logs.add(LOG_SUCCESS + ": " + log);
    events.onLogAdded.onCallEvent();
  }

  public void addWarningLog(final String log) {
    logs.add(LOG_WARNING + ": " + log);
    events.onLogAdded.onCallEvent();
  }

  public void addErrorLog(final String log) {
    logs.add(LOG_ERROR + ": " + log);
    events.onLogAdded.onCallEvent();
  }

  public void addInfoLog(final String log) {
    logs.add(LOG_INFO + ": " + log);
    events.onLogAdded.onCallEvent();
  }

  public API getAPI() {
    return api;
  }

  public API.LifecycleEvents getProjectLifecycleEvents() {
    return api.lifecycleEvents;
  }

  public static class InterpreterEvents extends Events {
    public Event onLogAdded = emptyEvent();

    public void setOnLogAdded(final Event onLogAdded) {
      this.onLogAdded = onLogAdded;
    }
  }
}
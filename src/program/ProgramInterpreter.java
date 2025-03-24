package dev.trindadedev.bshrunner.program;

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
import dev.trindadedev.bshrunner.event.Event;
import dev.trindadedev.bshrunner.event.Events;
import dev.trindadedev.bshrunner.util.FileUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProgramInterpreter extends Interpreter {

  public static final String PROGRAMS_PATH = "/sdcard/bsh";

  private static final String LOG_TASK = "[TASK]";
  private static final String LOG_SUCCESS = "[SUCCESS]";
  private static final String LOG_WARNING = "[WARNING]";
  private static final String LOG_ERROR = "[ERROR]";
  private static final String LOG_INFO = "[INFO]";

  public InterpreterEvents events;

  protected Context context;
  protected File programPath;
  protected List<String> logs;
  protected ProgramAPI api;

  public ProgramInterpreter(final Context context, final File programPath) throws EvalError {
    this(context, programPath, new InterpreterEvents());
  }

  public ProgramInterpreter(final Context context, final File programPath, InterpreterEvents events)
      throws EvalError {
    super();
    this.programPath = programPath;
    this.context = context;
    this.logs = new ArrayList<>();
    this.api = new ProgramAPI(context, this);
    this.events = events;
    configureVariables();
    addTaskLog("Environment variables defined.");
  }

  private void configureVariables() throws EvalError {
    set("programPath", programPath);
    set("context", context);
    set("api", api);
  }

  /** Compiles the main file of Program. */
  public void runProgramMain() throws EvalError {
    if (!getProgramMainFile().exists()) {
      addErrorLog("Program main.bsh File Not Exists!\n");
      return;
    }

    final var programMainContent = FileUtil.readFile(getProgramMainFile());

    if (programMainContent.isEmpty()) {
      addErrorLog("Empty Program main.bsh File!\n");
      return;
    }
    eval(programMainContent);

    addSuccessLog("Compiled with success!");

    if (api.program.getName() == null || api.program.getName().isEmpty()) {
      addWarningLog("Please provide Program Name");
    } else {
      addInfoLog("Running " + api.program.getName() + "...");
    }

    if (api.program.getDescription() == null || api.program.getDescription().isEmpty()) {
      addWarningLog("Please provide Program Description");
    } else {
      addInfoLog("Program Description: " + api.program.getDescription());
    }

    if (api.program.getApiVersion() == null) {
      addWarningLog("Please declare the api version of your program");
    } else {
      addInfoLog("Program API Version: " + api.program.getApiVersion().toString());
    }

    final String authorName = api.program.getAuthorName();
    final String authorUserName = api.program.getAuthorUserName();

    if ((authorName == null || authorName.isEmpty()) && (authorUserName == null || authorUserName.isEmpty())) {
      addWarningLog("Please provide Author Info");
    } else {
      addInfoLog("Program Author: " + (authorName != null ? authorName : "N/A") 
               + " (" + (authorUserName != null ? authorUserName : "N/A") + ")");
    }
  }

  /** Returns the main file of Program. */
  public File getProgramMainFile() {
    return new File(programPath, "main.bsh");
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

  public ProgramAPI.LifecycleEvents getProgramLifecycleEvents() {
    return api.lifecycleEvents;
  }

  public static class InterpreterEvents extends Events {
    public Event onLogAdded = emptyEvent();

    public void setOnLogAdded(final Event onLogAdded) {
      this.onLogAdded = onLogAdded;
    }
  }
}

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
import dev.trindadedev.eltrinity.beans.ProjectBean;
import dev.trindadedev.eltrinity.utils.FileUtil;
import dev.trindadedev.eltrinity.c2bsh.C2BSH;
import dev.trindadedev.eltrinity.project.api.API;
import dev.trindadedev.eltrinity.project.manage.ProjectManager;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ELTrinityInterpreter extends Interpreter {

  private static final String LOG_TASK = "[TASK]";
  private static final String LOG_SUCCESS = "[SUCCESS]";
  private static final String LOG_WARNING = "[WARNING]";
  private static final String LOG_ERROR = "[ERROR]";
  private static final String LOG_INFO = "[INFO]";

  protected Context context;
  
  protected ProjectBean project;
  protected File projectPath;

  protected List<String> logs;
  protected API api;
  protected InterpreterEvents events;

  public ELTrinityInterpreter(final Context context) throws EvalError {
    this(context, new ProjectBean(), new InterpreterEvents());
  }

  public ELTrinityInterpreter(final Context context, final ProjectBean project) throws EvalError {
    this(context, project, new InterpreterEvents());
  }

  public ELTrinityInterpreter(final Context context,
    final ProjectBean project, final InterpreterEvents events
  ) throws EvalError {
    super();
    this.logs = new ArrayList<>();
    this.context = context;
    this.api = new API(context, this);
    this.events = events;
    setProject(project);
    configureVariables();
    addTaskLog("Environment variables defined.");
  }

  public void setProject(final ProjectBean project) throws EvalError {
    this.project = project;

    if (project == null || project.basicInfo == null || project.basicInfo.name == null) {
      addErrorLog("Invalid project data: basicInfo or name is null");
      throw new EvalError("Invalid project: project name is null");
    }

    projectPath = new File(ProjectManager.getProjectsFile(), project.basicInfo.name);
    configureVariables();
  }

  public void setEvents(final InterpreterEvents events) {
    this.events = events;
  }

  private void configureVariables() throws EvalError {
    set("context", context);
    set("project", project);
    set("projectPath", projectPath);
    set("api", api);
  }

  /** Compiles main file of project. */
  public void runProjectMain() throws EvalError {
    final File file = getProjectMainFile();
    final String name = file.getName();
    if (name.endsWith(".bsh")) {
      runBSHFile(file);
    } else if (name.endsWith(".c")) {
      runCFile(file);
    }
  }

  /** Converts the C lang code to BeanShell Code and compile it. */
  protected void runCFile(final File file) throws EvalError {
    final String cCode = FileUtil.readFile(file);
    final String bshCode = C2BSH.convert(cCode);
    final File bshFile = new File(projectPath, "build/" + file.getName() + ".bsh");
    FileUtil.writeText(bshFile, bshCode);
    runBSHFile(bshFile);
  }

  /** Compiles an file of Project. */
  protected void runBSHFile(final File file) throws EvalError {
    if (project == null) {
      addErrorLog("Project not loaded successfully!");
      return;
    }

    if (projectPath == null) {
      addErrorLog("Project path is not set!");
      return;
    }

    if (!file.exists()) {
      addErrorLog(file.getName() + " File Not Exists!\n");
      return;
    }

    final String fileContent = FileUtil.readFile(file);

    if (fileContent.isEmpty()) {
      addErrorLog(file.getName() + " is Empty File!\n");
      return;
    }
    eval(fileContent);

    addSuccessLog("Compiled successfully!");

    if (project.basicInfo.name == null || project.basicInfo.name.isEmpty()) {
      addWarningLog("Please provide Project Name");
    } else {
      addInfoLog("Running " + project.basicInfo.name + "...");
    }

    if (project.basicInfo.description == null || project.basicInfo.description.isEmpty()) {
      addWarningLog("Please provide Project Description");
    } else {
      addInfoLog("Project Description: " + project.basicInfo.description);
    }

    final String authorName = project.basicInfo.authorName;
    final String authorUserName = project.basicInfo.authorUserName;

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

  public InterpreterEvents getInterpreterEvents() {
    return events;
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
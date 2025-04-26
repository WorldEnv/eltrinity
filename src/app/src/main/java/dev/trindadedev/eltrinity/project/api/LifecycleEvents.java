package dev.trindadedev.eltrinity.project.api;

/*
 * Copyright 2025 Aquiles Trindade (trindadedev).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import dev.trindadedev.eltrinity.project.Event;
import dev.trindadedev.eltrinity.project.Events;

public class LifecycleEvents extends Events {
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
    this.onPostCreate = onPostCreate;
  }
}
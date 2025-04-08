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

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import dev.trindadedev.eltrinity.project.ELTrinityInterpreter;
import dev.trindadedev.eltrinity.ui.base.BaseAppCompatActivity;

public abstract class BaseAPIActivity extends BaseAppCompatActivity {

  @Override
  protected void onBindLayout(@Nullable final Bundle savedInstanceState) {
    requireInterpreter().getProjectLifecycleEvents().onCreate.onCallEvent();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    requireInterpreter().getProjectLifecycleEvents().onDestroy.onCallEvent();
  }

  @Override
  protected void onResume() {
    super.onResume();
    requireInterpreter().getProjectLifecycleEvents().onResume.onCallEvent();
  }

  @Override
  protected void onStart() {
    super.onStart();
    requireInterpreter().getProjectLifecycleEvents().onStart.onCallEvent();
  }

  @Override
  protected void onPause() {
    super.onPause();
    requireInterpreter().getProjectLifecycleEvents().onPause.onCallEvent();
  }

  @Override
  protected void onStop() {
    super.onStop();
    requireInterpreter().getProjectLifecycleEvents().onStop.onCallEvent();
  }

  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    requireInterpreter().getProjectLifecycleEvents().onPostCreate.onCallEvent();
  }

  protected abstract ELTrinityInterpreter getInterpreter();

  protected final ELTrinityInterpreter requireInterpreter() {
    final ELTrinityInterpreter interpreter = getInterpreter();
    if (interpreter == null) {
      throw new IllegalStateException("ELTrinityInterpreter has not been initialized.");
    }
    return interpreter;
  }

  /**
   * Returns the Root View Used in API.
   *
   * @see API#addViewAtRoot(View)
   */
  @Nullable
  public ViewGroup getRootViewForApi() {
    return null;
  }
}

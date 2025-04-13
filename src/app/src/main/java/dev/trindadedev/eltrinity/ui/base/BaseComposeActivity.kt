package dev.trindadedev.eltrinity.ui.base

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

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import dev.trindadedev.eltrinity.ui.theme.ELTrinityTheme

public abstract class BaseComposeActivity: BaseAppCompatActivity() {

  // returns null and check if its null in BaseAppCompatActivity
  // bcz i too lazy to refactor it
  override fun bindLayout(): View? {
    return null
  }

  override fun onBindLayout(savedInstanceState: Bundle?) {
    setContent {
      ELTrinityTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
          onScreenCreated(savedInstanceState)
        }
      }
    }
  }

  @Composable
  public abstract fun onScreenCreated(savedInstanceState: Bundle?)
}
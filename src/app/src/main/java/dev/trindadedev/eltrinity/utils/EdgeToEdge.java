package dev.trindadedev.eltrinity.utils;

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

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import dev.trindadedev.eltrinity.ui.base.BaseAppCompatActivity;

public class EdgeToEdge {
  public static void enable(@NonNull BaseAppCompatActivity activity) {
    WindowCompat.setDecorFitsSystemWindows(activity.getWindow(), false);
    ViewCompat.setOnApplyWindowInsetsListener(
        activity.getWindow().getDecorView(),
        (a, insets) -> {
          var systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
          activity
              .getWindow()
              .getDecorView()
              .setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
          return insets;
        });
  }
}

package dev.trindadedev.eltrinity.ui.activities.debug

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

import android.os.Bundle
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.trindadedev.eltrinity.ui.base BaseComposeActivity
import dev.trindadedev.eltrinity.ui.theme.ELTrinityTheme

class DebugActivity : BaseComposeActivity() {
  override fun onScreenCreated(savedInstanceState: Bundle?) {
    val errorMessage = intent?.getStringExtra("error") ?: "No error message provided."
    ErrorMessageView(errorMessage)
  }
}

@Composable
private fun ErrorMessageView(message: String) {
  val verticalScroll = rememberScrollState()
  val horizontalScroll = rememberScrollState()

  Text(
    text = message,
    fontSize = 14.sp,
    modifier = Modifier
      .verticalScroll(verticalScroll)
      .horizontalScroll(horizontalScroll)
      .padding(16.dp),
    maxLines = Int.MAX_VALUE,
    overflow = TextOverflow.Clip
  )
}
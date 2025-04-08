package dev.trindadedev.eltrinity.ui.components.dialog;

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

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.core.view.WindowCompat;
import dev.trindadedev.eltrinity.R;
import dev.trindadedev.eltrinity.databinding.DialogProgressDialogBinding;
import dev.trindadedev.eltrinity.utils.StringUtil;

public class ProgressDialog extends Dialog {

  private DialogProgressDialogBinding binding;

  public ProgressDialog(@NonNull final Context context) {
    super(context);
    binding = DialogProgressDialogBinding.inflate(LayoutInflater.from(context));
    setContentView(binding.getRoot());
    setTitle(R.string.common_word_loading);
    super.setCancelable(false);

    var window = getWindow();
    if (window != null) {
      window.setBackgroundDrawableResource(android.R.color.transparent);
      window.setStatusBarColor(0);
      WindowCompat.setDecorFitsSystemWindows(window, false);
    }
  }

  @Override
  public void setTitle(final CharSequence charSeq) {
    binding.text.setText(charSeq);
  }

  @Override
  public void setTitle(@StringRes final int strResId) {
    setTitle(StringUtil.getString(strResId));
  }
}

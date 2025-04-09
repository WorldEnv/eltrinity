package dev.trindadedev.eltrinity.ui.activities.debug;

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

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;

public class DebugActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Intent intent = getIntent();

        if (intent != null) {
          final String errorMessage = intent.getStringExtra("error");
          if (!errorMessage.isEmpty()) {
            final TextView errorView = new TextView(this);
            errorView.setText(errorMessage);
            errorView.setTextIsSelectable(true);

            final HorizontalScrollView hscroll = new HorizontalScrollView(this);
            final ScrollView vscroll = new ScrollView(this);

            hscroll.addView(vscroll);
            vscroll.addView(errorView);

            setContentView(hscroll);
          }
        }
    }
}

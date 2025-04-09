package dev.trindadedev.eltrinity;

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

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Process;
import android.util.Log;
import dev.trindadedev.eltrinity.ui.activities.debug.DebugActivity;
import java.io.File;

public final class ELTrinity extends Application {

  private static Context mAppContext;

  @Override
  public void onCreate() {
    super.onCreate();
    appContext = this;
    Thread.setDefaultUncaughtExceptionHandler(
        new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable throwable) {
              final Intent intent = new Intent(getApplicationContext(), DebugActivity.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
              intent.putExtra("error", Log.getStackTraceString(throwable));
              startActivity(intent);
              Process.killProcess(Process.myPid());
              System.exit(1);
            }
        });
  }

  public static final String getPublicFolderPath() {
    return getPublicFolderFile().getAbsolutePath();
  }

  public static final File getPublicFolderFile() {
    return new File(Environment.getExternalStorageDirectory(), "eltrinity/");
  }

  public static final Context getAppContext() {
    return mAppContext;
  }
}

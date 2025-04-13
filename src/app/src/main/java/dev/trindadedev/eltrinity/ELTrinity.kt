package dev.trindadedev.eltrinity

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

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.os.Process
import android.util.Log
import dev.trindadedev.eltrinity.ui.activities.debug.DebugActivity
import java.io.File

class ELTrinity : Application() {

  override fun onCreate() {
    super.onCreate()
    mAppContext = this

    Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
      val intent = Intent(applicationContext, DebugActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        putExtra("error", Log.getStackTraceString(throwable))
      }
      startActivity(intent)
      Process.killProcess(Process.myPid())
      exitProcess(1)
    }
  }

  companion object {
    private lateinit var mAppContext: Context

    fun getPublicFolderPath(): String {
      return getPublicFolderFile().absolutePath
    }

    fun getPublicFolderFile(): File {
      return File(Environment.getExternalStorageDirectory(), "eltrinity/")
    }

    fun getAppContext(): Context {
      return mAppContext
    }
  }
}
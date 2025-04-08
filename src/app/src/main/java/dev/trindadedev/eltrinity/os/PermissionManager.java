package dev.trindadedev.eltrinity.os;

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

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;

public class PermissionManager {
  PermissionManager() {}

  public static final class Storage implements Permission {
    private final Activity activity;
    private final ActivityResultLauncher<Intent> storageAllLauncher;
    private final ActivityResultLauncher<String[]> storageReadWriteLauncher;

    public Storage(
        final Activity activity,
        final ActivityResultLauncher<Intent> storageAllLauncher,
        final ActivityResultLauncher<String[]> storageReadWriteLauncher) {
      this.activity = activity;
      this.storageAllLauncher = storageAllLauncher;
      this.storageReadWriteLauncher = storageReadWriteLauncher;
    }

    @Override
    public PermissionStatus check() {
      var statusBool = false;
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        statusBool = Environment.isExternalStorageManager();
      } else {
        statusBool =
            ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
                        activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED;
      }
      return (statusBool) ? PermissionStatus.GRANTED : PermissionStatus.DENIED;
    }

    @Override
    public final void request() {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        requestStorageAll();
      } else {
        requestStorageReadWrite();
      }
    }

    public final void requestStorageAll() {
      if (!Environment.isExternalStorageManager()) {
        var intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        storageAllLauncher.launch(intent);
      }
    }

    public final void requestStorageReadWrite() {
      String[] perms = {
        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
      };
      storageReadWriteLauncher.launch(perms);
    }
  }

  public static final class Overlay implements Permission {
    private final Activity activity;
    private final ActivityResultLauncher<Intent> overlayPermissionLauncher;

    public Overlay(
        final Activity activity,
        final ActivityResultLauncher<Intent> overlayPermissionLauncher) {
      this.activity = activity;
      this.overlayPermissionLauncher = overlayPermissionLauncher;
    }

    @Override
    public PermissionStatus check() {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        return Settings.canDrawOverlays(activity)
            ? PermissionStatus.GRANTED
            : PermissionStatus.DENIED;
      }
      return PermissionStatus.GRANTED;
    }

    @Override
    public final void request() {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(activity)) {
        var intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        overlayPermissionLauncher.launch(intent);
      }
    }
  }
}

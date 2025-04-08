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

import java.io.*;

public class FileUtil {

  /**
   * Reads the contents of a file and returns it as a String.
   *
   * @param file The file.
   * @return The file content as a String.
   */
  public static String readFile(File file) {
    if (!file.exists() || !file.isFile()) {
      return ""; // Return an empty string if the file doesn't exist.
    }

    StringBuilder sb = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line).append("\n"); // Keep original formatting with line breaks.
      }
    } catch (IOException e) {
      e.printStackTrace(); // In production, replace this with proper logging.
    }

    return sb.toString().trim(); // Trim to remove extra trailing newline.
  }

  /**
   * Creates a directory if it does not exist.
   *
   * @param dir The directory file.
   * @return true if the directory was created or already exists, false otherwise.
   */
  public static boolean makeDir(File dir) {
    return dir.exists() || dir.mkdirs();
  }

  /**
   * Creates a new file if it does not exist.
   *
   * @param file The file.
   */
  public static void createNewFile(File file) {
    if (!file.exists()) {
      try {
        final File parentFile = file.getParentFile();
        if (parentFile != null) {
          makeDir(parentFile);
        }
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static void createNewFileIfNotPresent(final File file) {
    int lastSep = file.getAbsolutePath().lastIndexOf(File.separator);
    if (lastSep > 0) {
      String dirPath = file.getAbsolutePath().substring(0, lastSep);
      makeDir(new File(dirPath));
    }

    try {
      if (!file.exists()) file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void writeText(final File file, final String text) {
    createNewFileIfNotPresent(file);

    try (FileWriter fileWriter = new FileWriter(file.getAbsolutePath(), false)) {
      fileWriter.write(text);
      fileWriter.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}

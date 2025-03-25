package dev.trindadedev.tbsh.util;

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
}

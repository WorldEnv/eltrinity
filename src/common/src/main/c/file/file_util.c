/**
 * Licensed under Apache 2.0.
 * Developed by Aquiles Trindade (trindadedev).
 * in 2025-04-08
 */

#include <stdio.h>
#include <stdlib.h>
#include "file_util.h"
#include "../android/android_log.h"

char* file_read_text(char* file_path) {
  long file_length;
  char line;
  char* buffer;

  FILE* file;
  file = fopen(file_path, "r");
  if (file == NULL) {
    LOGD("Can't read file\n");
    return NULL;
  }

  fseek(file, 0, SEEK_END);           // go to end
  file_length = ftell(file);          // get file size
  rewind(file);                       // go back to start

  buffer = malloc(file_length + 1);   // allocate memory for file text
  if (buffer == NULL) {
    LOGD("Failed to allocate memory");
    return NULL;
  }

  fread(buffer, 1, file_length, file); // read file into buffer
  buffer[file_length] = '\n';

  fclose(file);                        // close file stream?

  return buffer;
}
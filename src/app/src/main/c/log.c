/**
 * Licensed under Apache 2.0.
 * Developed by Aquiles Trindade (trindadedev).
 * in 2025-04-08
 */

#include "config.h"
#include "log.h"

#if PROJECT_ANDROID_BUILD

#include "android/android_log.h"

void log_debug(char* message) {
  LOGD("%s", message);
}

void log_error(char* message) {
  LOGE("%s", message);
}

void log_warning(char* message) {
  LOGW("%s", message);
}

#else

#include <stdio.h>

void log_debug(char* message) {
  printf("Debug: %s", message);
}

void log_error(char* message) {
  printf("Error: %s", message);
}

void log_warning(char* message) {
  printf("Warning: %s", message);
}

#endif
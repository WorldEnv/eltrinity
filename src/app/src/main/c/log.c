/**
 * Licensed under Apache 2.0.
 * Developed by Aquiles Trindade (trindadedev).
 * in 2025-04-08
 */

#include "log.h"

#ifdef __ANDROID__

#include "android/android_log.h"

void log_debug(char* message) {
  LOGD(message);
}

void log_error(char* message) {
  LOGE(message);
}

void log_warning(char* message) {
  LOGW(message);
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
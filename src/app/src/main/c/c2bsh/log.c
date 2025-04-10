/**
 * Licensed under Apache 2.0.
 * Developed by Aquiles Trindade (trindadedev).
 * Updated in 2025-04-10
 */

#include "config.h"
#include "log.h"

#include <stdarg.h>

#if PROJECT_ANDROID_BUILD

#include "android/android_log.h"

void log_debug(const char* format, ...) {
  va_list args;
  va_start(args, format);
  __android_log_vprint(ANDROID_LOG_DEBUG, "APP", format, args);
  va_end(args);
}

void log_error(const char* format, ...) {
  va_list args;
  va_start(args, format);
  __android_log_vprint(ANDROID_LOG_ERROR, "APP", format, args);
  va_end(args);
}

void log_warning(const char* format, ...) {
  va_list args;
  va_start(args, format);
  __android_log_vprint(ANDROID_LOG_WARN, "APP", format, args);
  va_end(args);
}

#else

#include <stdio.h>

void log_debug(const char* format, ...) {
  va_list args;
  va_start(args, format);
  printf("[DEBUG] ");
  vprintf(format, args);
  printf("\n");
  va_end(args);
}

void log_error(const char* format, ...) {
  va_list args;
  va_start(args, format);
  fprintf(stderr, "[ERROR] ");
  vfprintf(stderr, format, args);
  fprintf(stderr, "\n");
  va_end(args);
}

void log_warning(const char* format, ...) {
  va_list args;
  va_start(args, format);
  printf("[WARNING] ");
  vprintf(format, args);
  printf("\n");
  va_end(args);
}

#endif
/**
 * Licensed under Apache 2.0.
 * Developed by Aquiles Trindade (trindadedev).
 * Updated in 2025-04-10
 */

#ifndef LOG_H
#define LOG_H

#include <stdarg.h>

void log_debug(const char* format, ...);

void log_error(const char* format, ...);

void log_warning(const char* format, ...);

#endif
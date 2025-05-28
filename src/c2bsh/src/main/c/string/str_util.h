/**
 * Licensed under Apache 2.0.
 * Developed by Aquiles Trindade (trindadedev).
 * in 2025-04-08
 */

#ifndef STR_UTIL_H
#define STR_UTIL_H

#include <stdbool.h>

bool str_starts_with(char* str, char* prefix);

// Replaces an text in String (char*) by moving memory
void str_replace(char* str, const char* target, const char* replacement);

char* str_trim(char* str);

#endif
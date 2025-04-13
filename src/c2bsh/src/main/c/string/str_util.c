/**
 * Licensed under Apache 2.0.
 * Developed by Aquiles Trindade (trindadedev).
 * in 2025-04-08
 */

#include <ctype.h>
#include <stdbool.h>
#include <stdio.h>
#include <string.h>
#include "str_util.h"

bool str_starts_with(char* str, char* prefix) {
  return strncmp(prefix, str, strlen(prefix)) == 0;
}

// Replaces an text in String (char*) by moving memory
void str_replace(char* str, const char* target, const char* replacement) {
  char buffer[1024];                                               // Temporary buffer
  char* pos = strstr(str, target);
  if (!pos) return;                                                // Target not found

  size_t before_len = pos - str;
  size_t target_len = strlen(target);
  size_t replacement_len = strlen(replacement);

  
  strncpy(buffer, str, before_len);                                // Copy part before the target
  strcpy(buffer + before_len, replacement);                        // Add the replacement
  strcpy(buffer + before_len + replacement_len, pos + target_len); // Add the rest of the string after the target
  strcpy(str, buffer);                                             // Copy result back into original string
}

// trim string
char* str_trim(char* str) {
  while (isspace((unsigned char)*str)) str++;
  return str;
}
/**
 * Licensed under Apache 2.0.
 * Developed by Aquiles Trindade (trindadedev).
 * in 2025-04-08
 */

#include <stdbool.h>
#include <stdio.h>
#include <string.h>
#include "str_util.h"

bool str_starts_with(char* str, char* prefix) {
  return strncmp(prefix, str, strlen(prefix)) == 0;
}
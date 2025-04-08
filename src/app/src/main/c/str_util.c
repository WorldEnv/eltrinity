#include <stdbool.h>
#include <stdio.h>
#include "str_util.h"

bool str_starts_with(char* str, char* prefix) {
  return strncmp(prefix, str, strlen(prefix)) == 0;
}
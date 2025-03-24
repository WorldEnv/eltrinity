#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "converter.h"
#include "str_util.h"

c2bsh_result c2bsh_convert(const char* c_code) {
  char* line = strtok(c_code, "\n");
  char** includes;
  while (line != NULL) {
    if (str_starts_with(line, "#include")) {
      // add line to includes list
    }
    line = strtok(NULL, "\n");
  }
  c2bsh_result result;
  result.code = "";
  result.includes = includes;
  return result;
}
/**
 * Licensed under Apache 2.0.
 * Developed by Aquiles Trindade (trindadedev).
 * in 2025-04-08
 */

#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "converter.h"
#include "../string/str_util.h"

// Converts the C lang code to Bean shell code
// @param c_code The CLang Code.
// @returns the result
// @see c2bsh_converter_result struct in converter.h
c2bsh_converter_result* c2bsh_converter_convert(char* c_code) {
  c2bsh_converter_result* result = malloc(sizeof(c2bsh_converter_result));
  result->code = calloc(1, 1024 * 10);            // 10KB Buffer.
  result->includes = malloc(sizeof(char*) * 10);  // Max 100 includes.
  result->includes_count = 0;

  char* code_copy = strdup(c_code);
  char* line = strtok(code_copy, "\n");

  while (line != NULL) {
    // add includes in includes list for futures handles
    if (c2bsh_converter_check_include(result, line)) {
      c2bsh_converter_add_includes(result, line);
      line = strtok(NULL, "\n");
      continue;
    }

    char buffer[1024];
    strcpy(buffer, line);

    // replace c types with bsh/java types
    char* ptr;
    if ((ptr = strstr(buffer, "char*")) != NULL) {
      str_replace(ptr, "char*", "String");
    } else if ((ptr = strstr(buffer, "bool")) != NULL) {
      str_replace(ptr, "bool", "boolean");
    }

    // remove asterisks
    char* p = buffer;
    char* dest = buffer;
    while (*p) {
      if (*p != '*') {
        *dest++ = *p;
      }
      p++;
    }
    *dest = '\0';

    // concatenate generated code into result code.
    strcat(result->code, buffer);

    // concatenate like-break symbol.
    strcat(result->code, "\n");

    line = strtok(NULL, "\n");
  }
  free(code_copy);                                // desalocate code copy pointer
  return result;
}

void c2bsh_converter_add_includes(c2bsh_converter_result* result, char* line) {
  char* include_str = line + 8;
  while (*include_str == ' ') {
    include_str++;
  }
  result->includes[result->includes_count++] = include_str;
}

bool c2bsh_converter_check_include(c2bsh_converter_result* result, char* line) {
  return str_starts_with(line, "#include");
}

// free result allocated memory
void c2bsh_converter_close(c2bsh_converter_result* result) {
  free(result);
}
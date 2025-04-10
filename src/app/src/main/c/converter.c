/**
 * Licensed under Apache 2.0.
 * Developed by Aquiles Trindade (trindadedev).
 * in 2025-04-08
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "converter.h"
#include "str_util.h"

// Converts the C lang code to Bean shell code
// @param c_code The CLang Code.
// @returns the result
// @see c2bsh_result struct in converter.h
c2bsh_result* c2bsh_convert(char* c_code) {
  c2bsh_result* result = malloc(sizeof(c2bsh_result));
  result->code = calloc(1, 1024 * 10);            // 10KB Buffer.
  result->includes = malloc(sizeof(char*) * 10);  // Max 100 includes.
  result->includes_count = 0;

  char* code_copy = strdup(c_code);
  char* line = strtok(code_copy, "\n");

  while (line != NULL) {
    // add includes in includes list for futures handles
    if (str_starts_with(line, "#include")) {
      char* include_str = line + 8;
      while (*include_str == ' ') {
        include_str++;
      }
      result->includes[result->includes_count++] = include_str;
      line = strtok(NULL, "\n");
      continue;
    }

    char buffer[1024];
    strcpy(buffer, line);

    // replace c types with bsh/java types
    char* ptr;
    if ((ptr = strstr(buffer, "char*")) != NULL) {
      memmove(ptr + 6, ptr + 5, strlen(ptr + 5) + 1);
      memcpy(ptr, "String", 6);
    } else if ((ptr = strstr(buffer, "bool")) != NULL) {
      memmove(ptr + 7, ptr + 4, strlen(ptr + 4) + 3);
      memcpy(ptr, "boolean", 7);
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

// free result allocated memory
void c2bsh_close(c2bsh_result* result) {
  free(result);
}
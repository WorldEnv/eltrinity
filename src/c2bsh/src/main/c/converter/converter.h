/**
 * Licensed under Apache 2.0.
 * Developed by Aquiles Trindade (trindadedev).
 * in 2025-04-08
 */

#ifndef CONVERTER_H
#define CONVERTER_H

#include <stdbool.h>

typedef struct {
  char* code;        // the beanshell converted code
  char** includes;   // list with all includes
  int includes_count; // the size of list with all includes
} c2bsh_converter_result;

// Converts the C lang code to Bean shell code
// @param c_code The CLang Code
// @returns the result
// @see c2bsh_converter_result struct
c2bsh_converter_result* c2bsh_converter_convert(char* c_code);

// free result allocated memory.
void c2bsh_converter_close(c2bsh_converter_result* result);

bool c2bsh_converter_check_include(c2bsh_converter_result* result, char* line);

void c2bsh_converter_add_includes(c2bsh_converter_result* result, char* line);

#endif
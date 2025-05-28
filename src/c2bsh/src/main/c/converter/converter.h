/**
 * Licensed under Apache 2.0.
 * Developed by Aquiles Trindade (trindadedev).
 * in 2025-04-08
 */

#ifndef CONVERTER_H
#define CONVERTER_H

#define SUCCESS 1
#define ERROR 0

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
c2bsh_converter_result* c2bsh_converter_convert(const char* c_code);

// Checks if line starts with #include
// if true its a include
bool c2bsh_converter_check_include(c2bsh_converter_result* result, char* line);

// Add includes into includes list.
void c2bsh_converter_add_includes(c2bsh_converter_result* result, char* line);

// Replace C Types with BeanShell/Java Types in ptr
// Example: char* -> String
void c2bsh_converter_replace_ctypes(char* code_buffer, char* ptr);

// Converts C Array declaration to BeanShell/Java Array Decalaration
void c2bsh_converter_resolve_array_syntax(char* input, char* output);

// free result allocated memory.
void c2bsh_converter_close(c2bsh_converter_result* result);

#endif

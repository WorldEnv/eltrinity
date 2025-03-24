/**
 * Licensed under Apache 2.0.
 * Developed by Aquiles Trindade (trindadedev).
 * in 2025-04-08
 */

#ifndef CONVERTER_H
#define CONVERTER_H

typedef struct {
  char* code;      // the beanshell converted code
  char** includes; // list with all includes
} c2bsh_result;

// the main method to convert.
// @param c_code the c code to convert into beanshell.
c2bsh_result c2bsh_convert(const char* c_code);

#endif
#ifndef FORMATTER_H
#define FORMATTER_H

#include <stdio.h>
#include <stdbool.h>

// Adds indentation to the buffer based on the indentation level
void formatter_append_intent(char* buffer, int* pos, int indent_level);

// Formatts the code and returns it
char* formatter_format(char* code, size_t buffer_size, bool indent_multiline_comments);

#endif
/**
 * Licensed under Apache 2.0.
 * Developed by Aquiles Trindade (trindadedev).
 * in 2025-04-12
 */

#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include "formatter.h"
#include "../log.h"

// Adds indentation to the buffer based on the indentation level
void formatter_append_indent(char* buffer, int* pos, int indent_level) {
  for (int i = 0; i < indent_level; i++) {
    if (*pos < 100000) {
      buffer[(*pos)++] = ' ';
      buffer[(*pos)++] = ' ';
    }
  }
}

// Formatts the code and returns it
char* formatter_format(char* code, size_t buffer_size, bool indent_multiline_comments) {
  int open_braces = 0;
  bool processing_single_line_comment = false;
  bool processing_multi_line_comment = false;
  bool processing_escape = false;
  bool processing_char = false;
  bool processing_string = false;
  char* formatted_code = malloc(buffer_size);
  if (!formatted_code) {
    log_error("Failed to allocate memory for formatted_code");
    return NULL;
  }
  int pos = 0;
  //formatter_append_indent(formatted_code, &pos, 1);
  for (size_t i = 0; code[i] != '\0'; i++) {
    char ch = code[i];
    if (processing_single_line_comment) {
      formatted_code[pos++] = ch;
      if (ch == '\n') {
        formatter_append_indent(formatted_code, &pos, open_braces);
        processing_single_line_comment = false;
      }
    } else if (processing_multi_line_comment) {
      formatted_code[pos++] = ch;
      if (ch == '*' && code[i + 1] == '/') {
        formatted_code[pos++] = code[++i];
        processing_multi_line_comment = false;
      } else if (indent_multiline_comments && ch == '\n') {
        formatter_append_indent(formatted_code, &pos, open_braces);
      }
    } else if (processing_escape) {
      formatted_code[pos++] = ch;
      processing_escape = false;
    } else if (ch == '\\') {
      formatted_code[pos++] = ch;
      processing_escape = true;
    } else if (processing_char) {
      formatted_code[pos++] = ch;
      if (ch == '\\' && code[i + 1]) {
        formatted_code[pos++] = code[++i];  // skip escaped char
      } else if (ch == '\'') {
        processing_char = false;
      }
    } else if (processing_string) {
      formatted_code[pos++] = ch;
      if (ch == '\\' && code[i + 1]) {
        formatted_code[pos++] = code[++i];
      } else if (ch == '"') {
        processing_string = false;
      }
    } else {
      if (ch == '/' && code[i + 1]) {
        if (code[i + 1] == '/') {
          formatted_code[pos++] = ch;
          formatted_code[pos++] = code[i + 1];
          processing_single_line_comment = true;
          continue;
        }
        if (code[i + 1] == '*') {
          formatted_code[pos++] = ch;
          formatted_code[pos++] = code[i + 1];
          processing_multi_line_comment = true;
          continue;
        }
      }
      if (ch != '\n') {
        if (ch == '\'') {
          processing_char = true;
        } else if (ch == '"') {
          processing_string = true;
        } else if (ch == '{') {
          formatted_code[pos++] = ch;
          open_braces++;
          formatter_append_indent(formatted_code, &pos, open_braces);
        } else if (ch == '}') {
          formatted_code[pos++] = '\n';
          if (open_braces > 0) open_braces--;
          formatter_append_indent(formatted_code, &pos, open_braces);
          formatted_code[pos++] = ch;
          formatted_code[pos++] = '\n';
          formatter_append_indent(formatted_code, &pos, open_braces);
        } else {
          formatted_code[pos++] = ch;
        }
      } else {
        formatted_code[pos++] = ch;
        formatter_append_indent(formatted_code, &pos, open_braces);
      }
    }
  }
  formatted_code[pos] = '\0';
  return formatted_code;
}
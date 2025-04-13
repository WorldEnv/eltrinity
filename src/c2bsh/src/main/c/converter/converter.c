/**
 * Licensed under Apache 2.0.
 * Developed by Aquiles Trindade (trindadedev).
 * in 2025-04-08
 */

#include <ctype.h>
#include <stdbool.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "converter.h"
#include "../string/str_util.h"
#include "../format/formatter.h"
#include "../log.h"

// Converts the C lang code to Bean shell code
// @param c_code The CLang Code.
// @returns the result
// @see c2bsh_converter_result struct in converter.h
c2bsh_converter_result* c2bsh_converter_convert(char* c_code) {
  c2bsh_converter_result* result = malloc(sizeof(c2bsh_converter_result));
  size_t code_size = 1024 * 10;
  result->code = calloc(1, code_size);            // 10KB Buffer.
  result->includes = malloc(sizeof(char*) * 10);  // Max 100 includes.
  result->includes_count = 0;

  char* code_copy = strdup(c_code);
  char* line = strtok(code_copy, "\n");

  while (line != NULL) {
    while (*line == ' ' || *line == '\t') {
      line++;
    }
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
    c2bsh_converter_replace_ctypes(buffer, ptr);

    // resolves array syntax
    // basic check
    if (strstr(line, "[]") != NULL) {
      c2bsh_converter_resolve_array_syntax(line, buffer);
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
  char* formatted_code = formatter_format(result->code, code_size, true);
  if (formatted_code != NULL) {
    strcpy(result->code, formatted_code);
  } else {
    log_error("Formatted code too big, truncating or skipping copy.");
  }
  free(code_copy);                                // desalocate code copy pointer to avoid memory leaks
  free(formatted_code);                           // desalocate formatted code pointer to avoid memory leaks
  return result;
}

// Checks if line starts with #include
// if true its a include
bool c2bsh_converter_check_include(c2bsh_converter_result* result, char* line) {
  return str_starts_with(line, "#include");
}

// Add includes into includes list.
void c2bsh_converter_add_includes(c2bsh_converter_result* result, char* line) {
  char* include_str = line + 8;
  while (*include_str == ' ') {
    include_str++;
  }
  result->includes[result->includes_count++] = include_str;
}

// Replace C Types with BeanShell/Java Types in ptr
// Example: char* -> String
void c2bsh_converter_replace_ctypes(char* code_buffer, char* ptr) {
  if ((ptr = strstr(code_buffer, "char*")) != NULL) {
    str_replace(ptr, "char*", "String");
  } else if ((ptr = strstr(code_buffer, "bool")) != NULL) {
    str_replace(ptr, "bool", "boolean");
  }
}

// Converts C Array declaration to BeanShell/Java Array Decalaration
// example of input "  int variable[];" or "  int variable[5]"
// example of output "  int[] variable;" or "  int[5] variable;"
void c2bsh_converter_resolve_array_syntax(char* raw_line, char* output) {

  char* line = str_trim(raw_line);

  const char* brackets_start = strchr(line, '[');
  const char* brackets_end = strchr(line, ']');
  const char* line_end = strchr(line, ';');
  if (!line_end) {
    line_end = line + strlen(line);
  }

  if (!brackets_start || !brackets_end || !line_end || brackets_end > line_end) {
    log_error("Something is wrong in Array");
    return;
  }

  const char* first_space = strchr(line, ' ');
  const int array_type_size = (int) (first_space - line);
  const int array_name_size = (int) (brackets_start - first_space - 1);
  const int array_value_size = (int) (brackets_end - brackets_start - 1);

  if (array_type_size <= 0) {
    log_error("No type provided for the Array");
    return;
  }
  if (array_name_size <= 0) {
    log_error("No name provided for the Array");
    return;
  }

  char array_type[100] = {0};
  char array_name[100] = {0};
  char array_size[100] = {0};

  strncpy(
      array_type,
      line,
      first_space - line);

  strncpy(
      array_name,
      first_space + 1,
      array_name_size);

  if (array_value_size > 0) {
    strncpy(
        array_size,
        brackets_start + 1,
        array_value_size);
  }

  str_trim(array_type);
  str_trim(array_name);
  str_trim(array_size);

  if (strlen(array_type) == 0 || strlen(array_name) == 0) {
    log_error("Please provide Array Type & Array Name");
    return;
  }

  if (strlen(array_size) > 0) {
    char* regex = "%s[%s] %s;";
    if (!line_end) {
      regex = "%s[%s] %s";
    }
    sprintf(
      output,
      regex,
      array_type,
      array_name,
      array_type,
      array_size);
  } else {
    char* regex = "%s[] %s;";
    if (!line_end) {
      regex = "%s[] %s";
    }
    sprintf(
      output,
      regex,
      array_type,
      array_name);
  }
}

// free result allocated memory
void c2bsh_converter_close(c2bsh_converter_result* result) {
  free(result);
}
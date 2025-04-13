/**
 * Licensed under Apache 2.0.
 * Developed by Aquiles Trindade (trindadedev).
 * in 2025-04-08
 */

#include <stdio.h>
#include "log.h"
#include "file/file_util.h"
#include "converter/converter.h"

// the main used to test the program without android context
// execute it like:
// ./<binary> *anFile*
// real example: ./c2bsh testcode.c
int main(int argc, char** argv) {
  char* file_path = argv[1];
  if (file_path == NULL) {
    log_debug("Please provide file path\n");
    return 1;
  }

  char* c_code = file_read_text(file_path);

  log_debug("Provided file: %s\n\n", file_path);
  log_debug("C code: \n\n%s\n", c_code);

  c2bsh_converter_result* convert_result = c2bsh_converter_convert(c_code);

  // print includes related things
  log_debug("BeanShell Code: \n\n%s\n", convert_result->code);
  if (convert_result->includes_count > 0) {
    log_debug("Includes Count: %i\nIncludes:\n", convert_result->includes_count);
    for (int i = 0; convert_result->includes_count > i; i++) {
      char* include = convert_result->includes[i];
      log_debug("%s\n", include);
    }
  }
  c2bsh_converter_close(convert_result);
  free(c_code);
  return 0;
}
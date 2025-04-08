#include <stdio.h>
#include "file_util.h"
#include "converter.h"

int main(int argc, char** argv) {
  char* file_path = argv[1];
  if (file_path == NULL) {
    printf("Please provide file path");
    return 1;
  }

  const char* c_code = file_read_text(file_path);

  printf("Provided file: %s\n\n", file_path);
  printf("C code: \n\n%s\n", c_code);

  c2bsh_result convert_result = c2bsh_convert(c_code);

  printf("BeanShell Code: \n\n%s\n", convert_result.code);
  printf("C Includes: \n\n");
  for (int i = 0; convert_result.includes[i] != NULL; i++) {
    printf("%s\n", convert_result.includes[i]);
  }

  free(convert_result.includes);
  free(c_code);
  return 0;
}
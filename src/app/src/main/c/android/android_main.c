/**
 * Licensed under Apache 2.0.
 * Developed by Aquiles Trindade (trindadedev).
 * in 2025-04-08
 */

#include <stdio.h>
#include <jni.h>
#include "android_main.h"
#include "../converter.h"

JNIEXPORT jstring JNICALL Java_dev_trindadedev_eltrinity_c2bsh_C2BSH_convert
  (JNIEnv* env, jobject, jstring j_c_code) {
  const char* c_code = (*env)->GetStringUTFChars(env, j_c_code, nullptr);
  c2bsh_result convert_result = c2bsh_convert(c_code);
  return convert_result.code; // just return code for now
}
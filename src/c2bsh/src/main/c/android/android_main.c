/**
 * Licensed under Apache 2.0.
 * Developed by Aquiles Trindade (trindadedev).
 * in 2025-04-08
 */

#include <stdio.h>
#include <jni.h>
#include "android_main.h"
#include "../converter/converter.h"

JNIEXPORT jlong JNICALL
Java_dev_trindadedev_c2bsh_C2BSH_convert
  (JNIEnv* env, jobject, jstring j_c_code) {
  char* c_code = (*env)->GetStringUTFChars(env, j_c_code, nullptr);
  c2bsh_converter_result* result_ptr = c2bsh_converter_convert(c_code);
  return (jlong) result_ptr;
}

JNIEXPORT jstring JNICALL
Java_dev_trindadedev_c2bsh_C2BSH_getCode
  (JNIEnv* env, jobject, jlong result) {
  c2bsh_converter_result* result_ptr = (c2bsh_converter_result*) result;
  jstring bsh_code = (*env)->NewStringUTF(env, result_ptr->code);
  return bsh_code;
}

JNIEXPORT jobjectArray JNICALL
Java_dev_trindadedev_c2bsh_C2BSH_getIncludes
  (JNIEnv* env, jobject, jlong result) {
  c2bsh_converter_result* result_ptr = (c2bsh_converter_result*) result;
  jclass string_class = (*env)->FindClass(env, "java/lang/String");
  jobjectArray array = (*env)->NewObjectArray(env, result_ptr->includes_count, string_class, NULL);
  for (int i = 0; i < result_ptr->includes_count; i++) {
    jstring include_str = (*env)->NewStringUTF(env, result_ptr->includes[i]);
    (*env)->SetObjectArrayElement(env, array, i, include_str);
  }
  return array;
}

JNIEXPORT void JNICALL
Java_dev_trindadedev_c2bsh_C2BSH_close
  (JNIEnv* env, jobject, jlong result) {
  c2bsh_converter_result* result_ptr = (c2bsh_converter_result*) result;
  c2bsh_converter_close(result_ptr);
}
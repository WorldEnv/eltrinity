/**
 * Licensed under Apache 2.0.
 * Developed by Aquiles Trindade (trindadedev).
 * in 2025-04-08
 */

#ifndef ANDROID_MAIN_H
#define ANDROID_MAIN_H

#include <jni.h>

JNIEXPORT jlong JNICALL
Java_dev_trindadedev_eltrinity_c2bsh_C2BSH_convert
  (JNIEnv* env, jobject, jstring j_c_code);

JNIEXPORT jstring JNICALL
Java_dev_trindadedev_eltrinity_c2bsh_C2BSH_getCode
  (JNIEnv* env, jobject, jlong result);

JNIEXPORT jobjectArray JNICALL
Java_dev_trindadedev_eltrinity_c2bsh_C2BSH_getIncludes
  (JNIEnv* env, jobject, jlong result);

JNIEXPORT void JNICALL
Java_dev_trindadedev_eltrinity_c2bsh_C2BSH_close
  (JNIEnv* env, jobject, jlong result);

#endif
#ifndef ANDROID_MAIN_H
#define ANDROID_MAIN_H

#include <jni.h>

JNIEXPORT jstring JNICALL Java_dev_trindadedev_eltrinity_c2bsh_C2BSH_convert
  (JNIEnv* env, jobject, jstring j_c_code);

#endif
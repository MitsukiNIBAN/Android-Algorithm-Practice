//
// Created by Mitsuki on 2019/9/26.
//
#include <jni.h>
#include <vector>
#include <unistd.h>
#include <android/bitmap.h>

using namespace std;

#ifndef ANDROID_ALGORITHM_PRACTICE_HANOI_H
#define ANDROID_ALGORITHM_PRACTICE_HANOI_H

#endif //ANDROID_ALGORITHM_PRACTICE_HANOI_H


void
hanoi(JNIEnv *env, int n, vector<float> &currency, vector<float> &target, vector<float> &assist,
      jobject jcallback, int time, jobject bitmap) {
    if (n < 1) return;

    hanoi(env, n - 1, currency, assist, target, jcallback, time, bitmap);

    target.push_back(currency.back());
    currency.pop_back();

    jclass javaClass = env->GetObjectClass(jcallback);
    jmethodID javaCallbackId = env->GetMethodID(javaClass, "callback",
                                                "(Landroid/graphics/Bitmap;)V");
    env->CallVoidMethod(jcallback, javaCallbackId, bitmap);

    usleep(time);
    hanoi(env, n - 1, assist, target, currency, jcallback, time, bitmap);
}
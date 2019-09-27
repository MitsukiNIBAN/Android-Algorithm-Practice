//
// Created by Mitsuki on 2019/9/26.
//
#include "hanoi.h"
#include "help.h"

extern "C" JNIEXPORT jfloatArray JNICALL
Java_com_mitsuki_implement_hanoi_Hanoi_init(JNIEnv *env, jobject jobj, jint num) {
    jfloatArray jfa = env->NewFloatArray(num);
    jfloat *elems = env->GetFloatArrayElements(jfa, NULL);
    int i = 0;
    float step = (float) 360 / (num - 1);
    for (; i < num; i++) elems[i] = i * step;
    env->ReleaseFloatArrayElements(jfa, elems, 0);
    return jfa;
}

extern "C" JNIEXPORT void JNICALL
Java_com_mitsuki_implement_hanoi_Hanoi_move(JNIEnv *env, jobject jobj, jfloatArray currency,
                                            jobject jcallback) {
    float *array = env->GetFloatArrayElements(currency, NULL);
    jint len = env->GetArrayLength(currency);
    vector<float> cur(array, array + len);
    vector<float> ass;
    vector<float> tar;

    //再这里创建bitmap
    jobject temp_bitmap = fetchBitmap(env, 640, 480);
    hanoi(env, len, cur, ass, tar, jcallback, 0, temp_bitmap);
}





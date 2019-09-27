//
// Created by Mitsuki on 2019/9/27.
//

#include <jni.h>

#ifndef ANDROID_ALGORITHM_PRACTICE_BITMAP_H
#define ANDROID_ALGORITHM_PRACTICE_BITMAP_H

#endif //ANDROID_ALGORITHM_PRACTICE_BITMAP_H

jobject fetchBitmap(JNIEnv *env, int width, int height) {

    jclass bitmapCls = env->FindClass("android/graphics/Bitmap");
    jmethodID createBitmapFunction = env->GetStaticMethodID(bitmapCls, "createBitmap",
                                                            "(IILandroid/graphics/Bitmap$Config;)Landroid/graphics/Bitmap;");
    jstring configName = env->NewStringUTF("ARGB_8888");
    jclass bitmapConfigClass = env->FindClass("android/graphics/Bitmap$Config");
    jmethodID valueOfBitmapConfigFunction = env->GetStaticMethodID(
            bitmapConfigClass, "valueOf",
            "(Ljava/lang/String;)Landroid/graphics/Bitmap$Config;");

    jobject bitmapConfig = env->CallStaticObjectMethod(bitmapConfigClass,
                                                       valueOfBitmapConfigFunction, configName);

    jobject newBitmap = env->CallStaticObjectMethod(bitmapCls, createBitmapFunction, width, height,
                                                    bitmapConfig);
    return newBitmap;
}
#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_example_network_di_Koin_baseUrlJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string baseURL = "https://api.github.com/";
    return env->NewStringUTF(baseURL.c_str());
}

extern "C"
jstring
Java_com_example_network_di_Koin_tokenJNI(
        JNIEnv *env,
        jobject /* this */) {
//place your token here
    std::string token = "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";
    return env->NewStringUTF(token.c_str());
}
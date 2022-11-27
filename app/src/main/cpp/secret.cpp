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
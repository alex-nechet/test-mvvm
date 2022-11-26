#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_example_network_di_Koin_baseUrl(
        JNIEnv* env,
        jobject /* this */) {
    std::string baseURL = "https://api.github.com/";
    return env->NewStringUTF(baseURL.c_str());
}

extern "C"
jstring
Java_com_example_network_di_Koin_token(
        JNIEnv* env,
        jobject /* this */) {
//place your token here
    std::string token = "";
    return env->NewStringUTF(token.c_str());
}
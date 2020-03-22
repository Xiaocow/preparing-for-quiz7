//
// Created by Xing Long on 17/3/2020.
//

#include <jni.h>
#include <android/log.h>
#include <string>
#include <sstream>
#include "bigint.h"
#include <string>
#include <iostream>

BigInt factorial(BigInt n);

extern "C"
JNIEXPORT jstring JNICALL
Java_edu_singaporetech_factorial_FactorialActivity_factorialNRJNI(JNIEnv *env, jobject thiz, jstring input) {

    const char *cstr = env->GetStringUTFChars(input, NULL);
    BigInt inputLong = BigInt(std::string(cstr));

    std::stringstream stringStream;
    BigInt bigNumber = factorial(inputLong);
    stringStream << bigNumber;
    std::string outputString = stringStream.str();

    return env->NewStringUTF(outputString.c_str());
}

BigInt factorial(BigInt n) {
    if(n > 1)
        return  (BigInt) n * factorial(n - 1);
    else
        return (BigInt) 1;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_edu_singaporetech_factorial_FactorialActivity_factorialNIJNI(JNIEnv *env, jobject thiz, jstring input) {

    const char *cstr = env->GetStringUTFChars(input, NULL);
    BigInt inputLong = BigInt(std::string(cstr));

    std::stringstream stringStream;
    BigInt bigNumber = 1;
    for (BigInt i = 1; i <= inputLong; i += 1){
        bigNumber = bigNumber * i;
    }
    stringStream << bigNumber;
    std::string outputString = stringStream.str();

    return env->NewStringUTF(outputString.c_str());
}
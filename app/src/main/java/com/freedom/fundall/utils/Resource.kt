package com.freedom.fundall.utils

sealed class Resource<out T> {
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure<out T>(val errormessage: String) : Resource<T>()
    class init<out T>:Resource<T>()
}
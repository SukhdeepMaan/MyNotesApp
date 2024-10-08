package com.example.mynotesapp.base

sealed class ResultState<out T> {
    data object Loading : ResultState<Nothing>()
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Failure(val error: Throwable) : ResultState<Nothing>()
}

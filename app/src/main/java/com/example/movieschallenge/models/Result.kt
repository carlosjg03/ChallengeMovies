package com.example.movieschallenge.models

sealed class Result<out T : Any> {

    data class Success<T : Any>(val value: T) : Result<T>()

    data class Failure(val error: String) : Result<Nothing>()

}

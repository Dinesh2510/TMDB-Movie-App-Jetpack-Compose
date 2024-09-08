package com.app.movieapp.utlis

sealed class MovieState<out T> {
    data class Success<T>(val data: T) : MovieState<T>()
    data class Error(val message: String) : MovieState<Nothing>()
    object Loading : MovieState<Nothing>()
}
package com.morse.movie.data.entities.ui

sealed class State {
    object Loading : State()
    data class Error(val reason: String) : State()
    data class Success<ResponseType>(val response: ResponseType) : State()
}

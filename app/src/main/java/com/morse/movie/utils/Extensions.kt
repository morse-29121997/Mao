package com.morse.movie.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.morse.movie.data.entities.ui.State
import com.morse.movie.data.repository.BaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Response

fun CoroutineScope.execute(suspendFunction: suspend () -> Unit) {
    launch {
        suspendFunction.invoke()
    }
}

fun <Result, MapResult> BaseRepository.execute(
    executionApi: suspend () -> Response<Result>,
    mapFunctions: (Result?) -> MapResult
) = flow {
    val result = executionApi.invoke()
    if (result.isSuccessful) {
        emit(mapFunctions.invoke(result.body()))
    } else {
        println("Error , Can`t Execute the Api Because ${result.errorBody()?.string()}")
    }
}

fun <Result, MapResult> BaseRepository.executeState(
    executionApi: suspend () -> Response<Result>,
    mapFunctions: (Result?) -> MapResult
) = flow {
    val result = executionApi.invoke()
    if (result.isSuccessful) {
        emit(State.Success(mapFunctions.invoke(result.body())))
    } else {
        emit(State.Error(result.errorBody()?.string() ?: "Fail to load this request ."))
    }
}

fun <Result> BaseRepository.executeState(
    executionApi: suspend () -> Response<Result>
) = flow {
    val result = executionApi.invoke()
    if (result.isSuccessful) {
        emit(State.Success(result.body()))
    } else {
        emit(State.Error(result.errorBody()?.string() ?: "Fail to load this request ."))
    }
}
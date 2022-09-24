package com.morse.movie.data.repository

import com.morse.movie.data.entities.MoviesResponse
import com.morse.movie.remote.MoviesApis
import com.morse.movie.remote.RetrofitBuilder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

interface BaseRepository

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

interface IMoviesRepository : BaseRepository {
    fun loadPopularMovies(): Flow<ArrayList<MoviesResponse.Movie>>
}

class MoviesRepository(private val api: MoviesApis = RetrofitBuilder.getMoviesAPI()) :
    IMoviesRepository {
    override fun loadPopularMovies(): Flow<ArrayList<MoviesResponse.Movie>> {
        return execute({ api.getPopularMovie() }, {
            it?.results as ArrayList<MoviesResponse.Movie> /* = java.util.ArrayList<com.morse.movie.data.entities.MoviesResponse.Movie> */
        })
    }
}
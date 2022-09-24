package com.morse.movie.remote

import com.morse.movie.data.entities.MoviesResponse
import com.morse.movie.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

interface MoviesApis {
    @GET("movie/popular?api_key=${Constants.apiKey}&language=en-US&page=1")
    suspend fun getPopularMovie(): Response<MoviesResponse>
}
package com.morse.movie.remote

import com.morse.movie.data.entities.remote.CastResponse
import com.morse.movie.data.entities.remote.DetailsResponse
import com.morse.movie.data.entities.remote.MoviesResponse
import com.morse.movie.data.entities.remote.TVResponse
import com.morse.movie.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MaoApis {

    @GET("movie/now_playing?api_key=${Constants.apiKey}&language=en-US&page=1")
    suspend fun getNowMovie(): Response<MoviesResponse>

    @GET("movie/top_rated?api_key=${Constants.apiKey}&language=en-US&page=1")
    suspend fun getPopularMovie(): Response<MoviesResponse>

    @GET("tv/popular?api_key=${Constants.apiKey}&language=en-US&page=1")
    suspend fun getPopularTv(): Response<TVResponse>

    @GET("tv/top_rated?api_key=${Constants.apiKey}&language=en-US&page=1")
    suspend fun getNowPlayingTv(): Response<TVResponse>

    @GET("movie/{movieId}?api_key=${Constants.apiKey}&language=en-US")
    suspend fun getMovieDetails(@Path("movieId") movieId: Int): Response<DetailsResponse>

    @GET("movie/{movieId}/credits?api_key=${Constants.apiKey}&language=en-US")
    suspend fun getMovieCredits(@Path("movieId") movieId: Int): Response<CastResponse>

    @GET("tv/{tvId}?api_key=${Constants.apiKey}&language=en-US")
    suspend fun getTVDetails(@Path("tvId") tvId: Int): Response<DetailsResponse>

    @GET("/tv/{tvId}/similar?api_key=${Constants.apiKey}&language=en-US&page=1")
    suspend fun getTVSSimilar(@Path("tvId") tvId: Int): Response<TVResponse>

}
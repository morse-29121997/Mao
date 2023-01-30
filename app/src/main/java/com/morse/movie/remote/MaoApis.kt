package com.morse.movie.remote

import com.morse.movie.data.entities.remote.CastResponse
import com.morse.movie.data.entities.remote.MovieDetailsResponse
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
    suspend fun getMovieDetails(@Path("movieId") movieId: Int): Response<MovieDetailsResponse>

    @GET("movie/{movieId}/credits?api_key=${Constants.apiKey}&language=en-US")
    suspend fun getMovieCredits(@Path("movieId") movieId: Int): Response<CastResponse>

    @GET("tv/{tvId}?api_key=${Constants.apiKey}&language=en-US")
    suspend fun getTVDetails(@Path("tvId") tvId: Int): Response<MovieDetailsResponse>

    @GET("/tv/{tvId}/credits?api_key=${Constants.apiKey}&language=en-US")
    suspend fun getTVCredits(@Path("tvId") tvId: Int): Response<CastResponse>

}
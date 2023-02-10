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

    @GET("3/movie/now_playing?api_key=${Constants.apiKey}&language=en-US&page=1")
    suspend fun getNowMovie(): Response<MoviesResponse>

    @GET("3/movie/top_rated?api_key=${Constants.apiKey}&language=en-US&page=1")
    suspend fun getPopularMovie(): Response<MoviesResponse>

    @GET("3/tv/popular?api_key=${Constants.apiKey}&language=en-US&page=1")
    suspend fun getPopularTv(): Response<TVResponse>

    @GET("3/tv/top_rated?api_key=${Constants.apiKey}&language=en-US&page=1")
    suspend fun getNowPlayingTv(): Response<TVResponse>

    @GET("3/movie/{movieId}?api_key=${Constants.apiKey}&language=en-US")
    suspend fun getMovieDetails(@Path("movieId") movieId: Int): Response<DetailsResponse>

    @GET("3/movie/{movieId}/credits?api_key=${Constants.apiKey}&language=en-US")
    suspend fun getMovieCredits(@Path("movieId") movieId: Int): Response<CastResponse>

    @GET("3/tv/{tvId}?api_key=${Constants.apiKey}&language=en-US")
    suspend fun getTVDetails(@Path("tvId") tvId: Int): Response<DetailsResponse>

    @GET("3/tv/{tvId}/credits?api_key=${Constants.apiKey}&language=en-US&page=1")
    suspend fun getTVSCredits(@Path("tvId") tvId: Int): Response<CastResponse>

}
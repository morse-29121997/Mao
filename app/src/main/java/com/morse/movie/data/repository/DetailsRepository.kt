package com.morse.movie.data.repository

import com.morse.movie.data.entities.ui.State
import com.morse.movie.remote.MaoApis
import com.morse.movie.remote.RetrofitBuilder
import com.morse.movie.utils.executeState
import kotlinx.coroutines.flow.Flow


interface IDetailsRepository : BaseRepository {
    fun loadMovieDetails(movieId: Int): Flow<State>
    fun loadMovieCredits(movieId: Int): Flow<State>
    fun loadTVDetails(tvId: Int): Flow<State>
    fun loadTVSSimilar(tvId: Int): Flow<State>

}

class DetailsRepository(private val api: MaoApis = RetrofitBuilder.getMaoAPI()) :
    IDetailsRepository {

    override fun loadMovieDetails(movieId: Int): Flow<State> = executeState {
        api.getMovieDetails(movieId)
    }

    override fun loadMovieCredits(movieId: Int): Flow<State> = executeState {
        api.getMovieCredits(movieId)
    }

    override fun loadTVDetails(tvId: Int): Flow<State> = executeState {
        api.getTVDetails(tvId)
    }

    override fun loadTVSSimilar(tvId: Int): Flow<State> = executeState {
        api.getTVSSimilar(tvId)
    }
}
package com.morse.movie.data.repository

import com.morse.movie.data.entities.ui.State
import com.morse.movie.remote.MaoApis
import com.morse.movie.remote.RetrofitBuilder
import com.morse.movie.utils.executeState
import kotlinx.coroutines.flow.Flow

interface BaseRepository
interface IMoviesRepository : BaseRepository {
    fun loadPopularMovies():  Flow<State>
    fun loadNowMovies():  Flow<State>
}

class MoviesRepository(private val api: MaoApis = RetrofitBuilder.getMaoAPI()) :
    IMoviesRepository {
    override fun loadPopularMovies(): Flow<State> {
        return executeState({ api.getPopularMovie() }, {
            it?.results as ArrayList
        })
    }

    override fun loadNowMovies():  Flow<State> {
        return executeState({ api.getNowMovie() }, {
            it?.results as ArrayList
        })
    }
}
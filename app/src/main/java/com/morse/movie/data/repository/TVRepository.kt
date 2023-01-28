package com.morse.movie.data.repository

import com.morse.movie.data.entities.ui.State
import com.morse.movie.remote.MaoApis
import com.morse.movie.remote.RetrofitBuilder
import com.morse.movie.utils.executeState
import kotlinx.coroutines.flow.Flow

interface ITVRepository : BaseRepository {
    fun loadPopularTV(): Flow<State>
    fun loadNowPlayingTV(): Flow<State>
}

class TVRepository(private val api: MaoApis = RetrofitBuilder.getMaoAPI()) : ITVRepository {
    override fun loadPopularTV(): Flow<State> = executeState({ api.getPopularTv() }, {
        it?.results ?: arrayListOf()
    })

    override fun loadNowPlayingTV(): Flow<State> =
        executeState({ api.getNowPlayingTv() }, { it?.results ?: arrayListOf() })
}
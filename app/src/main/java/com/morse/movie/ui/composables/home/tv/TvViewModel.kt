package com.morse.movie.ui.composables.home.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morse.movie.data.entities.ui.State
import com.morse.movie.data.repository.ITVRepository
import com.morse.movie.data.repository.TVRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TvViewModel(private val repository: ITVRepository = TVRepository()) : ViewModel() {

    private val _popular = MutableSharedFlow<State>()
    val popular: Flow<State> get() = _popular

    private val _nowPlaying = MutableSharedFlow<State>()
    val nowPlaying: Flow<State> get() = _nowPlaying

    init {
        loadPopularTV()
        loadNowPlayingTV()
    }

    private fun loadPopularTV() {
        repository.loadPopularTV()
            .onEach { _popular.emit(it) }
            .launchIn(viewModelScope)
    }

    private fun loadNowPlayingTV() {
        repository.loadNowPlayingTV()
            .onEach { _nowPlaying.emit(it) }
            .launchIn(viewModelScope)
    }

}
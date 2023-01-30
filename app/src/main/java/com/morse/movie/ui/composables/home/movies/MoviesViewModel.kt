package com.morse.movie.ui.composables.home.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morse.movie.data.entities.remote.MoviesResponse
import com.morse.movie.data.entities.ui.State
import com.morse.movie.data.repository.IMoviesRepository
import com.morse.movie.data.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MoviesViewModel(private val repository: IMoviesRepository = MoviesRepository()) :
    ViewModel() {

    private val _popular = MutableSharedFlow<State>()
    val popular: Flow<State> get() = _popular

    private val _now = MutableSharedFlow<State>()
    val now: Flow<State> get() = _now

    fun load() {
        loadPopularMovies()
        loadNowMovies()
    }

    private fun loadPopularMovies() {
        repository.loadPopularMovies()
            .onEach { _popular.emit(it) }
            .launchIn(viewModelScope)
    }

    private fun loadNowMovies() {
        repository.loadNowMovies()
            .onEach { _now.emit(it) }
            .launchIn(viewModelScope)
    }

}
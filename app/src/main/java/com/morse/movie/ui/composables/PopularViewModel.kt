package com.morse.movie.ui.composables

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morse.movie.data.entities.MoviesResponse
import com.morse.movie.data.repository.IMoviesRepository
import com.morse.movie.data.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class PopularViewModel(private val repository: IMoviesRepository = MoviesRepository()) :
    ViewModel() {

    private val _popular = MutableSharedFlow<ArrayList<MoviesResponse.Movie>>()
    val popular: Flow<ArrayList<MoviesResponse.Movie>> get() = _popular

    fun loadMovies() {
        repository.loadPopularMovies()
            .onEach { }
            .launchIn(viewModelScope)
    }

}
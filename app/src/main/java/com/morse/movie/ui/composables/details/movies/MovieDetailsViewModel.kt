package com.morse.movie.ui.composables.details.movies

import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morse.movie.data.entities.ui.State
import com.morse.movie.data.repository.DetailsRepository
import com.morse.movie.data.repository.IDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MovieDetailsViewModel(
    private val repository: IDetailsRepository = DetailsRepository()
) : ViewModel() {

    private val _details = MutableSharedFlow<State>()
    val details: Flow<State> get() = _details

    private val _credits = MutableSharedFlow<State>()
    val credits: Flow<State> get() = _credits

    init {
        loadMovieDetails(676547)
        loadMovieCredits(676547)
    }

    private fun loadMovieDetails(id: Int) {
        repository.loadMovieDetails(id)
            .onEach { _details.emit(it) }
            .launchIn(viewModelScope)
    }

    private fun loadMovieCredits(id: Int) {
        repository.loadMovieCredits(id)
            .onEach { _credits.emit(it) }
            .launchIn(viewModelScope)
    }

}
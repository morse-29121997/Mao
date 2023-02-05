package com.morse.movie.ui.composables.details.tvs

import android.os.Bundle
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.morse.movie.data.entities.ui.State
import com.morse.movie.data.repository.DetailsRepository
import com.morse.movie.data.repository.IDetailsRepository
import com.morse.movie.ui.composables.details.movies.MovieDetailsViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class TVDetailsViewModel(
    private val stateHolder: SavedStateHandle,
    private val repository: IDetailsRepository = DetailsRepository()
) : ViewModel() {
    companion object Factory {
        class Instance(
            private val repository: IDetailsRepository = DetailsRepository(),
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null
        ) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
            override fun <T : ViewModel> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return TVDetailsViewModel(handle, repository) as T
            }
        }

    }

    private val _details = MutableSharedFlow<State>()
    val details: Flow<State> get() = _details

    private val _similars = MutableSharedFlow<State>()
    val similars: Flow<State> get() = _similars

    fun load (id : Int){
        loadTVDetails(id)
        loadTVSSimilars(id)
    }

    private fun loadTVDetails(id: Int) {
        repository.loadTVDetails(id)
            .onEach { _details.emit(it) }
            .launchIn(viewModelScope)
    }

    private fun loadTVSSimilars(id: Int) {
        repository.loadTVSSimilar(id)
            .onEach { _similars.emit(it) }
            .launchIn(viewModelScope)
    }

}
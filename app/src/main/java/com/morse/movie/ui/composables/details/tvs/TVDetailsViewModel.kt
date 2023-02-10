package com.morse.movie.ui.composables.details.tvs

import android.os.Bundle
import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.SavedStateRegistryOwner
import com.codelab.android.datastore.MediaItem
import com.morse.movie.data.entities.remote.DetailsResponse
import com.morse.movie.data.entities.ui.State
import com.morse.movie.data.repository.CacheRepository
import com.morse.movie.data.repository.DetailsRepository
import com.morse.movie.data.repository.ICacheRepository
import com.morse.movie.data.repository.IDetailsRepository
import com.morse.movie.ui.composables.details.movies.MovieDetailsViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TVDetailsViewModel(
    private val stateHolder: SavedStateHandle,
    private val remoteRepository: IDetailsRepository = DetailsRepository(),
    private val cacheRepository: ICacheRepository = CacheRepository(),
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

    private val _credits = MutableSharedFlow<State>()
    val credits: Flow<State> get() = _credits

    private val _isLiked = MutableSharedFlow<State>()
    val isLiked: Flow<State> get() = _isLiked

    private val _isStared = MutableSharedFlow<State>()
    val isStared: Flow<State> get() = _isStared

    fun load(id: Int) {
        isLiked(id)
        isStared(id)
        loadTVDetails(id)
        loadTVSCredits(id)
    }

    fun addMovieToLiked(movie: DetailsResponse) {
        viewModelScope.launch {
            cacheRepository.addLikedMediaItem(
                MediaItem.newBuilder()
                    .setId(movie.id)
                    .setIsMovie(false)
                    .setTitle(movie.title)
                    .setPoster(movie.getForegroundImage())
                    .build()
            )
        }
    }

    fun addMovieToStared(movie: DetailsResponse) {
        viewModelScope.launch {
            cacheRepository.addStaredMediaItem(
                MediaItem.newBuilder()
                    .setId(movie.id)
                    .setIsMovie(false)
                    .setTitle(movie.title)
                    .setPoster(movie.getForegroundImage())
                    .build()
            )
        }
    }

    fun removeMovieFromLiked(movie: DetailsResponse) {
        viewModelScope.launch {
            cacheRepository.removeLikedMediaItem(movie.id)
        }
    }

    fun removeMovieFromStared(movie: DetailsResponse) {
        viewModelScope.launch {
            cacheRepository.removeStaredMediaItem(movie.id)
        }
    }

    private fun isLiked(id: Int) {
        cacheRepository.isLiked(id)
            .onEach { _isLiked.emit(State.Success(it)) }
            .launchIn(viewModelScope)
    }

    private fun isStared(id: Int) {
        cacheRepository.isStared(id)
            .onEach { _isStared.emit(State.Success(it)) }
            .launchIn(viewModelScope)
    }

    private fun loadTVDetails(id: Int) {
        remoteRepository.loadTVDetails(id)
            .onEach { _details.emit(it) }
            .launchIn(viewModelScope)
    }

    private fun loadTVSCredits(id: Int) {
        remoteRepository.loadTVSCredits(id)
            .onEach { _credits.emit(it) }
            .launchIn(viewModelScope)
    }

}
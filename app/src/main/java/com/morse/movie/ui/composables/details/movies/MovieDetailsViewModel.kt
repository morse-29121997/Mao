package com.morse.movie.ui.composables.details.movies

import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.lifecycle.ViewModel
import com.morse.movie.data.repository.DetailsRepository
import com.morse.movie.data.repository.IDetailsRepository

class MovieDetailsViewModel(
    private val stateHolder: SaveableStateHolder,
    private val repository: IDetailsRepository = DetailsRepository()
) : ViewModel() {
}
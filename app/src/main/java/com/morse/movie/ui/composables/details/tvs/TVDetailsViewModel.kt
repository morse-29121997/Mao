package com.morse.movie.ui.composables.details.tvs

import androidx.compose.runtime.saveable.SaveableStateHolder
import androidx.lifecycle.ViewModel
import com.morse.movie.data.repository.DetailsRepository
import com.morse.movie.data.repository.IDetailsRepository

class TVDetailsViewModel(
    private val stateHolder: SaveableStateHolder,
    private val repository: IDetailsRepository = DetailsRepository()
) : ViewModel() {

}
package com.morse.movie.ui.composables.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.morse.movie.data.repository.CacheRepository
import com.morse.movie.data.repository.ICacheRepository
import kotlinx.coroutines.launch

class OnBoardingViewModel(private val cacheRepository: ICacheRepository = CacheRepository()) :
    ViewModel() {

    fun toggle() {
        viewModelScope.launch {
            cacheRepository.toggleGetStartedValue()
        }
    }

}
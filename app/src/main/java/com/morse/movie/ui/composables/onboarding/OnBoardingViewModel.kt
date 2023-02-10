package com.morse.movie.ui.composables.onboarding

import androidx.lifecycle.ViewModel
import com.morse.movie.data.repository.CacheRepository
import com.morse.movie.data.repository.ICacheRepository

class OnBoardingViewModel(private val cacheRepository: ICacheRepository = CacheRepository()) :
    ViewModel() {

    suspend fun toggle() {
        cacheRepository.toggleGetStartedValue()
    }

}
package com.morse.movie.ui.composables.splash

import androidx.lifecycle.ViewModel
import com.morse.movie.data.repository.CacheRepository
import com.morse.movie.data.repository.ICacheRepository

class SplashViewModel(private val cacheRepository: ICacheRepository = CacheRepository()) :
    ViewModel() {

    val isStartedBefore = cacheRepository.isGetStartedPressed()

}
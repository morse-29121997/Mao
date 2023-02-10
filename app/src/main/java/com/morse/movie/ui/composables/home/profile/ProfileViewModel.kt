package com.morse.movie.ui.composables.home.profile

import androidx.lifecycle.ViewModel
import com.morse.movie.data.repository.CacheRepository
import com.morse.movie.data.repository.ICacheRepository

class ProfileViewModel(private val cacheRepository: ICacheRepository = CacheRepository()) :
    ViewModel() {

    val liked = cacheRepository.loadLikedMediaItems()
    val stared = cacheRepository.loadStaredMediaItems()

}
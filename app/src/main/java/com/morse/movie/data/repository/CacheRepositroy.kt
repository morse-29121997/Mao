package com.morse.movie.data.repository

import com.codelab.android.datastore.MediaItem
import com.morse.movie.app.instance
import com.morse.movie.local.LocalDataStore
import kotlinx.coroutines.flow.Flow

interface ICacheRepository {

    // GetStarted Boolean
    fun isGetStartedPressed(): Flow<Boolean>
    suspend fun toggleGetStartedValue()

    // Save TVs Shows and Movies Items
    suspend fun addLikedMediaItem(item: MediaItem)
    suspend fun addStaredMediaItem(item: MediaItem)
    suspend fun removeLikedMediaItem(itemId: Int)
    suspend fun removeStaredMediaItem(itemId: Int)
    fun isLiked(itemId: Int): Flow<Boolean>
    fun isStared(itemId: Int): Flow<Boolean>
    fun loadLikedMediaItems(): Flow<List<MediaItem>>
    fun loadStaredMediaItems(): Flow<List<MediaItem>>
}

class CacheRepository(private val cache: LocalDataStore = instance) : ICacheRepository {

    override fun isGetStartedPressed(): Flow<Boolean> = cache.isGetStartedBefore()

    override suspend fun toggleGetStartedValue() {
        cache.toggleConfig()
    }

    override suspend fun addLikedMediaItem(item: MediaItem) {
        cache.addLikedMediaItem(item)
    }

    override suspend fun addStaredMediaItem(item: MediaItem) {
        cache.addStaredMediaItem(item)
    }

    override suspend fun removeLikedMediaItem(itemId: Int) {
        cache.removeLikedMediaItem(itemId)
    }

    override suspend fun removeStaredMediaItem(itemId: Int) {
        cache.removeStaredMediaItem(itemId)
    }

    override fun isLiked(itemId: Int): Flow<Boolean> = cache.isLiked(itemId)

    override fun isStared(itemId: Int): Flow<Boolean> = cache.isStared(itemId)

    override fun loadLikedMediaItems(): Flow<List<MediaItem>> = cache.loadLikedMediaItems()

    override fun loadStaredMediaItems(): Flow<List<MediaItem>> = cache.loadStaredMediaItems()
}
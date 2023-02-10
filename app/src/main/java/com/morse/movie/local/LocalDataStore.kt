package com.morse.movie.local

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.codelab.android.datastore.Config
import com.codelab.android.datastore.MediaItem
import com.codelab.android.datastore.MediaItems
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import java.io.IOException

class LocalDataStore(private val context: Context) {

    private val Context.configProtoStore: DataStore<Config> by dataStore(
        fileName = Config::class.java.name,
        serializer = ConfigurationSerializable
    )

    private val Context.likedMediaItemsProtoStore: DataStore<MediaItems> by dataStore(
        fileName = "Liked${MediaItems::class.java.name}",
        serializer = MediaItemsSerializable
    )

    private val Context.staredMediaItemsProtoStore: DataStore<MediaItems> by dataStore(
        fileName = "Stared${MediaItems::class.java.name}",
        serializer = MediaItemsSerializable
    )

    fun isGetStartedBefore() =
        context.configProtoStore.data.map { it.isGetStarted }.catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e("TAG", "Error reading sort order preferences.", exception)
                emit(Config.getDefaultInstance().isGetStarted)
            } else {
                throw exception
            }

        }

    suspend fun toggleConfig() {
        context.configProtoStore.updateData {
            it.toBuilder()
                .setIsGetStarted(it.isGetStarted.not())
                .build()
        }
    }

    fun loadLikedMediaItems() =
        context.likedMediaItemsProtoStore.data.map { it.itemsList }.catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e("TAG", "Error reading sort order preferences.", exception)
                emit(MediaItems.getDefaultInstance().itemsList)
            } else {
                throw exception
            }

        }

    fun loadStaredMediaItems() =
        context.staredMediaItemsProtoStore.data.map { it.itemsList }.catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e("TAG", "Error reading sort order preferences.", exception)
                emit(MediaItems.getDefaultInstance().itemsList)
            } else {
                throw exception
            }

        }

    suspend fun addLikedMediaItem( item: MediaItem) {
        context.likedMediaItemsProtoStore.updateData { mediaItems ->
            if (mediaItems.toBuilder().itemsList.contains(item)) {
                mediaItems
            } else {
                mediaItems.toBuilder().addItems(item).build()
            }
        }
    }

    suspend fun addStaredMediaItem( item: MediaItem) {
        context.staredMediaItemsProtoStore.updateData { mediaItems ->
            if (mediaItems.toBuilder().itemsList.contains(item)) {
                mediaItems
            } else {
                mediaItems.toBuilder().addItems(item).build()
            }
        }
    }

    suspend fun removeLikedMediaItem( id: Int) {
        context.likedMediaItemsProtoStore.updateData { items ->
            val existingItems = items.toBuilder().itemsList
            if (existingItems.any { it.id == id }) {
                val newList = existingItems.filter { it.id != id }
                items.toBuilder().clear().addAllItems(newList).build()
            } else {
                items
            }
        }
    }

    suspend fun removeStaredMediaItem( id: Int) {
        context.staredMediaItemsProtoStore.updateData { items ->
            val existingItems = items.toBuilder().itemsList
            if (existingItems.any { it.id == id }) {
                val newList = existingItems.filter { it.id != id }
                items.toBuilder().clear().addAllItems(newList).build()
            } else {
                items
            }
        }
    }

    fun isLiked( id: Int) = context.likedMediaItemsProtoStore.data
        .map { it.itemsList }
        .map { it.any { existItem -> existItem.id == id } }


    fun isStared( id: Int) = context.staredMediaItemsProtoStore.data
        .map { it.itemsList }
        .map { it.any { existItem -> existItem.id == id } }

}
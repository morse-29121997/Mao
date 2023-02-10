package com.morse.movie.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.codelab.android.datastore.Config
import com.codelab.android.datastore.MediaItem
import com.codelab.android.datastore.MediaItems
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object ConfigurationSerializable : Serializer<Config> {
    override val defaultValue: Config
        get() = Config.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Config {
        try {
            return Config.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Config, output: OutputStream) {
        t.writeTo(output)
    }
}

object MediaItemsSerializable : Serializer<MediaItems> {
    override val defaultValue: MediaItems
        get() = MediaItems.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): MediaItems {
        try {
            return MediaItems.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: MediaItems, output: OutputStream) {
        t.writeTo(output)
    }
}
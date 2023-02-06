package com.morse.movie.data.entities.ui

import android.content.ClipData.Item
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.morse.movie.R

data class Statics(
    @StringRes val title: Int,
    val value: Int,
    var isSelected: MutableState<Boolean> = mutableStateOf(false)
) {
    companion object {
        val Items = arrayListOf(
            Statics(R.string.like, 3210),
            Statics(R.string.watching, 1231),
            Statics(R.string.comment, 44)
        )

        fun unselectTab() {
            Items.apply {
                onEach {
                    if (it.isSelected.value) {
                        it.isSelected.value = false
                    }
                }
            }
        }

        fun selectTab(statics: Statics) {
            Items.apply {
                onEach {
                    if (it.title == statics.title && it.value == statics.value) {
                        it.isSelected.value = true
                    }
                }
            }
        }
    }
}
package com.morse.movie.data.entities.ui

import android.content.ClipData.Item
import androidx.annotation.StringRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.morse.movie.R

data class Statics(
    @StringRes val title: Int,
    var value: Int,
    var isSelected: MutableState<Boolean> = mutableStateOf(false)
) {
    companion object {
        val Items = arrayListOf(
            Statics(R.string.like, 0, mutableStateOf(true)),
            Statics(R.string.stared, 0),
            Statics(R.string.comment, 0)
        )

        fun updateLikeValue(value: Int) {
            Items[0].apply {
                this.value = value
            }
        }

        fun updateStarValue(value: Int) {
            Items[1].apply {
                this.value = value
            }
        }

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
package com.morse.movie.data.entities.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class MediaActionsStatus(
    val liked : MutableState<Boolean> = mutableStateOf(false) ,
    val stared : MutableState<Boolean> = mutableStateOf(false) ,
    val comments : MutableState<Boolean> = mutableStateOf(false) ,
)
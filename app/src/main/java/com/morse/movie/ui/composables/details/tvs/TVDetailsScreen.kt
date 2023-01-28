package com.morse.movie.ui.composables.details.tvs

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun TVDetailsScreen(controller: NavHostController? = null) {
    val id = controller?.currentBackStackEntryAsState()?.value?.arguments?.getInt("tvId")
    Box(contentAlignment = Alignment.Center) {
        Text(text = id.toString())
    }
}
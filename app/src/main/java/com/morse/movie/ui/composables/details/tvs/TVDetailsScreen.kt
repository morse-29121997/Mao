package com.morse.movie.ui.composables.details.tvs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.morse.movie.data.entities.remote.DetailsResponse
import com.morse.movie.data.entities.ui.State
import com.morse.movie.ui.composables.details.movies.RenderDetails
import com.morse.movie.ui.composables.home.shared.Loading
import com.morse.movie.utils.LoadFromVM

@Composable
fun TVDetailsScreen(
    controller: NavHostController? = null,
    vm: TVDetailsViewModel
) {
    val tvId = controller?.currentBackStackEntry?.arguments?.getInt("tvId") ?: 240
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val details = vm.details.collectAsState(initial = State.Loading)
        val similars = vm.similars.collectAsState(initial = State.Loading)
        val loading = createRef()
        LoadFromVM(tvId) {
            if (tvId > 0) {
                vm.load(tvId)
            }
        }
        when {
            details.value is State.Loading || similars.value is State.Loading -> Loading(
                modifier = Modifier.constrainAs(
                    loading
                ) {
                    linkTo(parent.top, parent.bottom)
                    linkTo(parent.start, parent.end)
                })
            else -> {
                RenderDetails(
                    {controller?.popBackStack()} ,
                    (details.value as State.Success<DetailsResponse>).response,
                    arrayListOf()
                )
            }
        }
    }
}
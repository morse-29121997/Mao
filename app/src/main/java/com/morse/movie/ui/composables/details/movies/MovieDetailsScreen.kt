package com.morse.movie.ui.composables.details.movies

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.morse.movie.data.entities.remote.CastResponse
import com.morse.movie.data.entities.remote.DetailsResponse
import com.morse.movie.data.entities.ui.MediaActionsStatus
import com.morse.movie.data.entities.ui.State
import com.morse.movie.ui.composables.home.shared.Loading
import com.morse.movie.ui.composables.home.shared.RenderDetails
import com.morse.movie.utils.LoadFromVM

@Composable
fun MovieDetailsScreen(
    controller: NavHostController? = null,
    vm: MovieDetailsViewModel
) {
    val movieId = controller?.currentBackStackEntry?.arguments?.getInt("movieId") ?: 240
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val details = vm.details.collectAsState(initial = State.Loading)
        val credits = vm.credits.collectAsState(initial = State.Loading)
        val loading = createRef()
        LoadFromVM(movieId) {
            if (movieId > 0) {
                vm.load(movieId)
            }
        }
        when {
            details.value is State.Loading || credits.value is State.Loading -> Loading(
                modifier = Modifier.constrainAs(
                    loading
                ) {
                    linkTo(parent.top, parent.bottom)
                    linkTo(parent.start, parent.end)
                })
            else -> {
                RenderDetails(
                    { controller?.popBackStack() },
                    (details.value as State.Success<DetailsResponse>).response,
                    (credits.value as State.Success<CastResponse>).response.cast ,
                    MediaActionsStatus()
                )
            }
        }
    }
}

package com.morse.movie.ui.composables.details.tvs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.morse.movie.data.entities.remote.CastResponse
import com.morse.movie.data.entities.remote.DetailsResponse
import com.morse.movie.data.entities.ui.MediaActionsStatus
import com.morse.movie.data.entities.ui.State
import com.morse.movie.ui.composables.home.shared.Error
import com.morse.movie.ui.composables.home.shared.Loading
import com.morse.movie.ui.composables.home.shared.RenderDetails
import com.morse.movie.utils.ExecuteFromVM

@Composable
fun TVDetailsScreen(
    controller: NavHostController? = null,
    vm: TVDetailsViewModel
) {
    val tvId = controller?.currentBackStackEntry?.arguments?.getInt("tvId") ?: 240
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val details = vm.details.collectAsState(initial = State.Loading)
        val credits = vm.credits.collectAsState(initial = State.Loading)
        val isLiked = vm.isLiked.collectAsState(initial = State.Loading)
        val isStared = vm.isStared.collectAsState(initial = State.Loading)
        val (loading , error) = createRefs()
        ExecuteFromVM(tvId) {
            if (tvId > 0) {
                vm.load(tvId)
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
            details.value is State.Error || credits.value is State.Error -> {
                Error(modifier = Modifier.constrainAs(error) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                } ) {
                    if (tvId > 0) {
                        vm.load(tvId)
                    }
                }
            }
            else -> {
                val details = (details.value as State.Success<DetailsResponse>).response
                val cast = (credits.value as State.Success<CastResponse>).response.cast
                RenderDetails(
                    { controller?.popBackStack() },
                    details,
                    cast,
                    MediaActionsStatus().apply {
                        liked.value = (isLiked.value as State.Success<Boolean>).response
                        stared.value = (isStared.value as State.Success<Boolean>).response
                    },
                    onLikedPressed = {
                        when (it) {
                            true -> {
                                vm.addMovieToLiked(details)
                            }
                            false -> {
                                vm.removeMovieFromLiked(details)
                            }
                        }
                    },
                    onStaredPressed = {when (it) {
                        true -> {
                            vm.addMovieToStared(details)
                        }
                        false -> {
                            vm.removeMovieFromStared(details)
                        }
                    }},
                    onCommentedPressed = {},
                )
            }
        }
    }
}
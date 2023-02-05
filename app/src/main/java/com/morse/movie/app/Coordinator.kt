package com.morse.movie.app

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.morse.movie.ui.composables.details.movies.MovieDetailsScreen
import com.morse.movie.ui.composables.details.movies.MovieDetailsViewModel
import com.morse.movie.ui.composables.details.tvs.TVDetailsScreen
import com.morse.movie.ui.composables.details.tvs.TVDetailsViewModel
import com.morse.movie.ui.composables.home.host.HomeScreen
import com.morse.movie.ui.composables.home.movies.MoviesScreen
import com.morse.movie.ui.composables.home.profile.ProfileScreen
import com.morse.movie.ui.composables.home.tv.TVsScreen
import com.morse.movie.ui.composables.onboarding.OnBoardingScreen
import com.morse.movie.ui.composables.splash.SplashScreen


@Composable
fun MaoCoordinator(controller: NavHostController) {
    NavHost(
        navController = controller,
        startDestination = SplashDirection.name,
        route = ApplicationRoute
    ) {
        composable(SplashDirection.name) {
            SplashScreen(controller)
        }
        composable(OnBoardingDirection.name) {
            OnBoardingScreen(controller)
        }
        composable(HomeDirection.name) {
            HomeScreen(controller)
        }
        composable(MovieDetailsDirection.name, arguments = listOf(navArgument("movieId") {
            type = NavType.IntType
        })) {
            MovieDetailsScreen(
                controller,
                viewModel(factory = MovieDetailsViewModel.Factory.Instance(owner = it))
            )
        }
        composable(TVDetailsDirection.name, arguments = listOf(navArgument("tvId") {
            type = NavType.IntType
        })) {
            TVDetailsScreen(
                controller,
                viewModel(factory = TVDetailsViewModel.Factory.Instance(owner = it))
            )
        }
    }
}

@Composable
fun HomeCoordinator(tabController: NavHostController, detailsController: NavHostController) {

    NavHost(
        navController = tabController,
        startDestination = HomeDirection.MoviesDirection.name,
        route = HomeRoute
    ) {
        composable(HomeDirection.MoviesDirection.name) {
            MoviesScreen(detailsController)
        }
        composable(HomeDirection.TVsDirection.name) {
            TVsScreen(detailsController)
        }
        composable(HomeDirection.ProfileDirection.name) {
            ProfileScreen(detailsController)
        }
    }
}

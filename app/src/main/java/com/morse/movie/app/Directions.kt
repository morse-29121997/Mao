package com.morse.movie.app

import androidx.navigation.*

const val ApplicationRoute = "Application-Route"
const val HomeRoute = "Home-Route"

interface Directions {
    val name: String
    fun navigate(navigationController: NavHostController?): Unit? = null
    companion object {
        fun findDirection(routeName: String) = when (routeName) {
            SplashDirection.name -> SplashDirection
            OnBoardingDirection.name -> OnBoardingDirection
            HomeDirection.name -> HomeDirection
            HomeDirection.MoviesDirection.name -> HomeDirection.MoviesDirection
            HomeDirection.TVsDirection.name -> HomeDirection.TVsDirection
            HomeDirection.ProfileDirection.name -> HomeDirection.ProfileDirection
            else -> SplashDirection
        }
    }
}

object SplashDirection : Directions {
    override val name: String
        get() = "Splash-Screen"

    override fun navigate(navigationController: NavHostController?) {
        navigationController?.navigate(OnBoardingDirection.name) {
            popUpTo(name) {
                inclusive = true
            }
        }
    }
}

object OnBoardingDirection : Directions {
    override val name: String
        get() = "OnBoarding-Screen"

    override fun navigate(navigationController: NavHostController?) {
        navigationController?.navigate(HomeDirection.name) {
            popUpTo(name) {
                inclusive = true
            }
        }
    }
}

object HomeDirection : Directions {

    override val name: String
        get() = "Home-Screen"

    object MoviesDirection : Directions {

        override val name: String
            get() = "Movies-Tab"

        override fun navigate(navigationController: NavHostController?) {
            navigationController?.navigate(name)
        }

    }

    object TVsDirection : Directions {
        override val name: String
            get() = "TVs-Tab"

        override fun navigate(navigationController: NavHostController?) {
            navigationController?.navigate(name)
        }
    }

    object ProfileDirection : Directions {
        override val name: String
            get() = "Profile-Tab"

        override fun navigate(navigationController: NavHostController?) {
            navigationController?.navigate(name)
        }
    }
}

object MovieDetailsDirection : Directions {
    override val name: String
        get() = "MovieDetailsScreen/{movieId}"
    private var movieId: Int = 0
    private val navigationName: String by lazy {
        "MovieDetailsScreen/$movieId"
    }

    fun injectMovieId(id: Int) {
        movieId = id
    }

    override fun navigate(navigationController: NavHostController?) {
        navigationController?.navigate(route = navigationName)
    }
}

object TVDetailsDirection : Directions {
    override val name: String
        get() = "TV-Details-Screen/{tvId}"
    private var tvId: Int = 0
    private val navigationName: String by lazy {
        "TV-Details-Screen/${tvId}"
    }

    fun injectTVId(id: Int) {
        tvId = id
    }

    override fun navigate(navigationController: NavHostController?) {
        navigationController?.navigate(navigationName)
    }
}
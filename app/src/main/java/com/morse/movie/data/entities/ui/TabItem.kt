package com.morse.movie.data.entities.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.morse.movie.R
import com.morse.movie.app.HomeDirection

data class TabItem(
    val routeName : String ,
    @StringRes val label: Int,
    @DrawableRes val unselectedIcon: Int,
    @DrawableRes val selectedIcon: Int
) {
    companion object {
        fun tabs() = arrayListOf(
            TabItem(
                HomeDirection.MoviesDirection.name ,
                R.string.movies_lable,
                R.drawable.movies_unselected,
                R.drawable.movies_selected,
            ),
            TabItem(
                HomeDirection.TVsDirection.name ,
                R.string.tv_lable,
                R.drawable.tv_unselected,
                R.drawable.tv_selected,
            ),
            TabItem(
                HomeDirection.ProfileDirection.name ,
                R.string.profile_lable,
                R.drawable.profiile_unselected,
                R.drawable.profile_selected,
            ),
        )
    }
}
package com.morse.movie.data.entities.remote

import com.morse.movie.utils.Constants

data class CastResponse(
    val id: Int,
    val cast: ArrayList<Cast>,
    val crew: ArrayList<Crew>
) {
    data class Cast(
        val id: Int,
        val name: String,
        val profile_path: String
    ) {
        fun getPosterPath() = "${Constants.imageApiPoster}/$profile_path"
    }

    data class Crew(
        val id: Int,
        val name: String,
        val profile_path: String
    )

}
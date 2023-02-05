package com.morse.movie.data.entities.remote

import com.google.gson.annotations.SerializedName
import com.morse.movie.utils.Constants

data class DetailsResponse(
    @SerializedName("title" , alternate = ["name"])
    val title: String,
    val overview: String,
    val popularity: Double,
    val vote_average: Double,
    val video: Boolean,
    val backdrop_path: String
) {
    fun getBackgroundImage() = "${Constants.imageApiBackground}/$backdrop_path"
    fun getVoteDecimal() = vote_average.toString().split('.').first()
    fun getVoteFraction() = ".${vote_average.toString().split('.').last()}"
}
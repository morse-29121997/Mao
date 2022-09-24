package com.morse.movie.data.entities


import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page")
    val page: Int = 0, // 1
    @SerializedName("results")
    val results: List<Movie> = listOf(),
    @SerializedName("total_pages")
    val totalPages: Int = 0, // 35201
    @SerializedName("total_results")
    val totalResults: Int = 0 // 704010
)  {

    data class Movie(
        @SerializedName("adult")
        val adult: Boolean = false, // false
        @SerializedName("backdrop_path")
        val backdropPath: String = "", // /2RSirqZG949GuRwN38MYCIGG4Od.jpg
        @SerializedName("genre_ids")
        val genreIds: List<Int> = listOf(),
        @SerializedName("id")
        val id: Int = 0, // 985939
        @SerializedName("original_language")
        val originalLanguage: String = "", // en
        @SerializedName("original_title")
        val originalTitle: String = "", // Fall
        @SerializedName("overview")
        val overview: String = "", // For best friends Becky and Hunter, life is all about conquering fears and pushing limits. But after they climb 2,000 feet to the top of a remote, abandoned radio tower, they find themselves stranded with no way down. Now Becky and Hunter’s expert climbing skills will be put to the ultimate test as they desperately fight to survive the elements, a lack of supplies, and vertigo-inducing heights
        @SerializedName("popularity")
        val popularity: Double = 0.0, // 6059.21
        @SerializedName("poster_path")
        val posterPath: String = "", // /spCAxD99U1A6jsiePFoqdEcY0dG.jpg
        @SerializedName("release_date")
        val releaseDate: String = "", // 2022-08-11
        @SerializedName("title")
        val title: String = "", // Fall
        @SerializedName("video")
        val video: Boolean = false, // false
        @SerializedName("vote_average")
        val voteAverage: Double = 0.0, // 7.4
        @SerializedName("vote_count")
        val voteCount: Int = 0 // 878
    )
}
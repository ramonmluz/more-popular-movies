package org.themoviedb.api.morepopularmoviesapp.model

import com.google.gson.annotations.SerializedName

data class Movie(
        @SerializedName("id") val id:Long,
        @SerializedName("overview") val overview:String,
        @SerializedName("poster_path") val posterPath:String,
        @SerializedName("original_title") val originalTitle:String,
        @SerializedName("vote_average") val voteAverage:String,
        @SerializedName("release_date") val releaseDate:String
)


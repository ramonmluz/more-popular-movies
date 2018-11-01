package org.themoviedb.api.morepopularmoviesapp.util

import android.util.Log
import java.text.SimpleDateFormat

class Util {

    companion object {
        fun getReleaseYear(realeaseDate: String): String {
            val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy")
            return simpleDateFormat.format(simpleDateFormat.parse(realeaseDate))
        }

    }
}
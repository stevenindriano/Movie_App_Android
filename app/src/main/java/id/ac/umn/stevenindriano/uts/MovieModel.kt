package id.ac.umn.stevenindriano.uts

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieModel(
    val title: String?,
    val description: String?,
    val thumbnail: String?,
    val genre: List<String>?,
    val year: Int,
    val rating: String?,
) : Parcelable
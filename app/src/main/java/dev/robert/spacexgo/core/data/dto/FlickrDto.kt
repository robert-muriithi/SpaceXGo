package dev.robert.spacexgo.core.data.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlickrDto(
    @SerializedName("original")
    val original: List<String>,
    @SerializedName("small")
    val small: List<String?>
) : Parcelable
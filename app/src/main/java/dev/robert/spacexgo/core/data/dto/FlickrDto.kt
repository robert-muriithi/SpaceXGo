package dev.robert.spacexgo.core.data.dto


import com.google.gson.annotations.SerializedName

data class FlickrDto(
    @SerializedName("original")
    val original: List<String>,
    @SerializedName("small")
    val small: List<String?>
)
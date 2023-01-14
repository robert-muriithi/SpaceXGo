package dev.robert.spacexgo.core.data.dto


import com.google.gson.annotations.SerializedName

data class PatchDto(
    @SerializedName("large")
    val large: String,
    @SerializedName("small")
    val small: String
)
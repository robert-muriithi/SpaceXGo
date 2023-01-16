package dev.robert.spacexgo.core.data.dto


import com.google.gson.annotations.SerializedName

data class CrewDto(
    @SerializedName("crew")
    val crew: String,
    @SerializedName("role")
    val role: String
)
package dev.robert.spacexgo.core.data.dto


import com.google.gson.annotations.SerializedName

data class HeadquartersDto(
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("state")
    val state: String
)
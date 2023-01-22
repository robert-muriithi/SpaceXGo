package dev.robert.spacexgo.core.data.dto


import com.google.gson.annotations.SerializedName

data class LatestLaunchesDto(
    @SerializedName("auto_update")
    val autoUpdate: Boolean,
    @SerializedName("capsules")
    val capsules: List<String>,
    @SerializedName("crew")
    val crew: List<CrewDto>,
    @SerializedName("details")
    val details: Any,
    @SerializedName("flight_number")
    val flightNumber: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("net")
    val net: Boolean,
    @SerializedName("payloads")
    val payloads: List<String>,
    @SerializedName("rocket")
    val rocket: String,

)
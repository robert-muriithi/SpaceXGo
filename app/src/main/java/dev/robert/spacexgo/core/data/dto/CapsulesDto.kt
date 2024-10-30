package dev.robert.spacexgo.core.data.dto


import com.google.gson.annotations.SerializedName

data class CapsulesDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("land_landings")
    val landLandings: Int,
    @SerializedName("last_update")
    val lastUpdate: String,
    @SerializedName("launches")
    val launches: List<String>?,
    @SerializedName("reuse_count")
    val reuseCount: Int,
    @SerializedName("serial")
    val serial: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("water_landings")
    val waterLandings: Int
)
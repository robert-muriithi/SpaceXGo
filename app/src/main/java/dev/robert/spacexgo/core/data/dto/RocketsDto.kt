package dev.robert.spacexgo.core.data.dto


import com.google.gson.annotations.SerializedName

data class RocketsDto(
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("company")
    val costPerLaunch: Int,
    @SerializedName("country")
    val country: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("flickr_images")
    val flickrImages: List<String>,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("stages")
    val stages: Int,
    @SerializedName("success_rate_pct")
    val successRatePct: Int,
    @SerializedName("type")
    val type: String,
)
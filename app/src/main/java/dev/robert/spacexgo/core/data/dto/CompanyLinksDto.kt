package dev.robert.spacexgo.core.data.dto


import com.google.gson.annotations.SerializedName

data class CompanyLinksDto(
    @SerializedName("elon_twitter")
    val elonTwitter: String,
    @SerializedName("flickr")
    val flickr: String,
    @SerializedName("twitter")
    val twitter: String,
    @SerializedName("website")
    val website: String
)
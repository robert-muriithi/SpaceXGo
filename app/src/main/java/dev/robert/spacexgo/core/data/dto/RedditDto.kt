package dev.robert.spacexgo.core.data.dto


import com.google.gson.annotations.SerializedName

data class RedditDto(
    @SerializedName("campaign")
    val campaign: Any,
    @SerializedName("launch")
    val launch: String,
    @SerializedName("media")
    val media: Any,
    @SerializedName("recovery")
    val recovery: Any
)
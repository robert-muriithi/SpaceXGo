package dev.robert.spacexgo.core.data.dto


import com.google.gson.annotations.SerializedName

data class HistoryLinksDto(
    @SerializedName("article")
    val article: String?
)
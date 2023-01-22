package dev.robert.spacexgo.core.data.dto


import com.google.gson.annotations.SerializedName

data class HistoryDto(
    @SerializedName("details")
    val details: String,
    @SerializedName("event_date_utc")
    val eventDateUtc: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("links")
    val links: HistoryLinksDto,
    @SerializedName("title")
    val title: String
)
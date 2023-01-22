package dev.robert.spacexgo.features.company.domain.model

import com.google.gson.annotations.SerializedName
import dev.robert.spacexgo.core.data.dto.HistoryLinksDto

data class History(
    val details: String,
    val eventDateUtc: String,
    val id: String,
    val links: HistoryLinks,
    val title: String
)

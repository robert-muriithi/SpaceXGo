package dev.robert.spacexgo.features.company.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.robert.spacexgo.core.utils.Constants
import dev.robert.spacexgo.features.company.domain.model.HistoryLinks

@Entity(tableName = Constants.HISTORY_TABLE)
data class HistoryEntity(
    val details: String,
    val eventDateUtc: String,
   @PrimaryKey val id: String,
    val links: HistoryLinksEntity,
    val title: String
)

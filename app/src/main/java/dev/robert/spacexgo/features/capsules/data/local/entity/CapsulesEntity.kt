package dev.robert.spacexgo.features.capsules.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.robert.spacexgo.core.utils.Constants


@Entity(tableName = Constants.CAPSULES_TABLE)
data class CapsulesEntity(
    @PrimaryKey val id: String,
    val landLandings: Int,
    val lastUpdate: String?,
    val launches: List<String?>,
    val reuseCount: Int,
    val serial: String?,
    val status: String?,
    val type: String?,
    val waterLandings: Int
)

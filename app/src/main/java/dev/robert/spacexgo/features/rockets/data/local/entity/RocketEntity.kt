package dev.robert.spacexgo.features.rockets.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.robert.spacexgo.core.utils.Constants

@Entity(tableName = Constants.ROCKETS_TABLE)
data class RocketEntity(
    val active: Boolean,
    val costPerLaunch: Int,
    val country: String,
    val description: String,
    val flickrImages: List<String>,
   @PrimaryKey val id: String,
    val name: String,
    val stages: Int,
    val successRatePct: Int,
    val type: String,
)

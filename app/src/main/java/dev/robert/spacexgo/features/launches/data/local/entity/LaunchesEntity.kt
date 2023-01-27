package dev.robert.spacexgo.features.launches.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import dev.robert.spacexgo.core.data.dto.CrewDto
import dev.robert.spacexgo.core.data.dto.LinksDto
import dev.robert.spacexgo.core.utils.Constants

@Entity(tableName = Constants.LAUNCHES_TABLE)
data class LaunchesEntity(
    val autoUpdate: Boolean,
    val capsules: List<String>,
    val dateLocal: String,
    val datePrecision: String,
    val dateUtc: String,
    val details: String?,
    val flightNumber: Int,
   @PrimaryKey val id: String,
    val launchLibraryId: String?,
    val launchpad: String,
    val linksDto: LinksDto,
    val name: String,
    val net: Boolean,
    val payloads: List<String>,
    val rocket: String,
    val ships: List<String?>,
    val success: Boolean,
    val upcoming: Boolean,
    val crew: List<CrewDto>,
)
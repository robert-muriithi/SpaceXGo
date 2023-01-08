package dev.robert.spacexgo.features.ships.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.robert.spacexgo.core.utils.Constants

@Entity(tableName = Constants.SHIP_TABLE)
data class ShipEntity(
    val active: Boolean,
    @PrimaryKey val id: String,
    @ColumnInfo(name = "shipImage", defaultValue = "")
    val shipImage: String?,
    val lastAisUpdate: String?,
    val launches: List<String?>,
    val massKg: Int,
    val model: String?,
    val name: String,
    val roles: List<String?>,
    val speedKn: String?,
    val status: String?,
    val type: String?,
    val yearBuilt: Int
)

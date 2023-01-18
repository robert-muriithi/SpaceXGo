package dev.robert.spacexgo.features.company.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

data class HeadquarterEntity(
    val address: String,
    val city: String,
    val state: String
)

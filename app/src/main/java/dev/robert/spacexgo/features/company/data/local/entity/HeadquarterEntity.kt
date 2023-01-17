package dev.robert.spacexgo.features.company.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class HeadquarterEntity(
    @PrimaryKey(autoGenerate = true) val id : Int,
    val address: String,
    val city: String,
    val state: String
)

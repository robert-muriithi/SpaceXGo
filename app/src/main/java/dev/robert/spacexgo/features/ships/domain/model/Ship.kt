package dev.robert.spacexgo.features.ships.domain.model

import com.google.gson.annotations.SerializedName

data class Ship(
    val active: Boolean,
    val id: String,
    val image: String?,
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

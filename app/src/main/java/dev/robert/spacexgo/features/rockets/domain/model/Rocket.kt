package dev.robert.spacexgo.features.rockets.domain.model



data class Rocket(
    val active: Boolean,
    val costPerLaunch: Int,
    val country: String,
    val description: String,
    val flickrImages: List<String>,
    val id: String,
    val name: String,
    val stages: Int,
    val successRatePct: Int,
    val type: String,
)

package dev.robert.spacexgo.features.rockets.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Rocket(
    val active: Boolean,
    val country: String,
    val description: String,
    val flickrImages: List<String>,
    val id: String,
    val name: String,
    val stages: Int,
    val successRatePct: Int,
    val type: String,
) : Parcelable

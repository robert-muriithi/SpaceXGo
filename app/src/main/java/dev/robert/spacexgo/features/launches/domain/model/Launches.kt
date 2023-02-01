package dev.robert.spacexgo.features.launches.domain.model

import android.os.Parcelable
import dev.robert.spacexgo.core.data.dto.CrewDto
import dev.robert.spacexgo.core.data.dto.LinksDto
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Launches(
    val autoUpdate: Boolean,
    val capsules: List<String>,
    val dateLocal: String,
    val datePrecision: String,
    val dateUtc: String,
    val details: String?,
    val flightNumber: Int,
    val id: String,
    val launchLibraryId: String?,
    val launchpad: String,
    val linksDto: LinksDto?,
    val name: String,
    val net: Boolean,
    val payloads: List<String>,
    val rocket: String,
    val ships: List<String?>,
    val success: Boolean,
    val upcoming: Boolean,
    val crew: List<CrewDto>,
) : Parcelable

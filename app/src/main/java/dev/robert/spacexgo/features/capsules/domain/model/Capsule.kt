package dev.robert.spacexgo.features.capsules.domain.model

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Capsule(
    val id: String,
    val landLandings: Int,
    val lastUpdate: String?,
    val launches: List<String?>,
    val reuseCount: Int,
    val serial: String?,
    val status: String?,
    val type: String?,
    val waterLandings: Int
) : Parcelable

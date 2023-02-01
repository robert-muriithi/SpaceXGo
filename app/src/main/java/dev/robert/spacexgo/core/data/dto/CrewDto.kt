package dev.robert.spacexgo.core.data.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CrewDto(
    @SerializedName("crew")
    val crew: String,
    @SerializedName("role")
    val role: String
): Parcelable
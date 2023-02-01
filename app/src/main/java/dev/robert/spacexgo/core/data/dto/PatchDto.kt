package dev.robert.spacexgo.core.data.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PatchDto(
    @SerializedName("large")
    val large: String,
    @SerializedName("small")
    val small: String
) : Parcelable
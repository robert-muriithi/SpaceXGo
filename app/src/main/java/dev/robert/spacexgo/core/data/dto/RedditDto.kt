package dev.robert.spacexgo.core.data.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RedditDto(
    @SerializedName("campaign")
    val campaign: String?,
    @SerializedName("launch")
    val launch: String?,
    @SerializedName("media")
    val media: String?,
    @SerializedName("recovery")
    val recovery: String?
) : Parcelable
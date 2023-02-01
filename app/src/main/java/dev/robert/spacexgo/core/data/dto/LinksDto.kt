package dev.robert.spacexgo.core.data.dto


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class LinksDto(
    @SerializedName("article")
    val article: String?,
    @SerializedName("flickr")
    val flickr:  FlickrDto?,
    @SerializedName("patch")
    val patchDto: PatchDto?,
    @SerializedName("presskit")
    val presskit: String?,
    @SerializedName("reddit")
    val redditDto: RedditDto,
    @SerializedName("webcast")
    val webcast: String,
    @SerializedName("wikipedia")
    val wikipedia: String,
    @SerializedName("youtube_id")
    val youtubeId: String
) : Parcelable
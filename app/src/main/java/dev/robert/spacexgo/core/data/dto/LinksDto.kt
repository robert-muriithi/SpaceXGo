package dev.robert.spacexgo.core.data.dto


import com.google.gson.annotations.SerializedName

data class LinksDto(
    @SerializedName("article")
    val article: Any,
    @SerializedName("flickr")
    val flickr: Flickr,
    @SerializedName("patch")
    val patchDto: PatchDto,
    @SerializedName("presskit")
    val presskit: Any,
    @SerializedName("reddit")
    val redditDto: RedditDto,
    @SerializedName("webcast")
    val webcast: String,
    @SerializedName("wikipedia")
    val wikipedia: String,
    @SerializedName("youtube_id")
    val youtubeId: String
)
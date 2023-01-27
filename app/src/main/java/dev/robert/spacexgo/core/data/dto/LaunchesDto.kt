package dev.robert.spacexgo.core.data.dto


import com.google.gson.annotations.SerializedName

data class LaunchesDto(
    @SerializedName("auto_update")
    val autoUpdate: Boolean,
    @SerializedName("capsules")
    val capsules: List<String>,
    @SerializedName("date_local")
    val dateLocal: String,
    @SerializedName("date_precision")
    val datePrecision: String,
    @SerializedName("date_unix")
    val dateUnix: Int,
    @SerializedName("date_utc")
    val dateUtc: String,
    @SerializedName("details")
    val details: String?,
    @SerializedName("failures")
    val failures: List<Any>,
    @SerializedName("fairings")
    val fairings: Any,
    @SerializedName("flight_number")
    val flightNumber: Int,
    @SerializedName("id")
    val id: String,
    @SerializedName("launch_library_id")
    val launchLibraryId: String?,
    @SerializedName("launchpad")
    val launchpad: String,
    @SerializedName("links")
    val linksDto: LinksDto,
    @SerializedName("name")
    val name: String,
    @SerializedName("net")
    val net: Boolean,
    @SerializedName("payloads")
    val payloads: List<String>,
    @SerializedName("rocket")
    val rocket: String,
    @SerializedName("ships")
    val ships: List<String?>,
    @SerializedName("static_fire_date_unix")
    val staticFireDateUnix: Any,
    @SerializedName("static_fire_date_utc")
    val staticFireDateUtc: Any,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("tbd")
    val tbd: Boolean,
    @SerializedName("upcoming")
    val upcoming: Boolean,
    @SerializedName("window")
    val window: Any,
    @SerializedName("crew")
    val crew: List<CrewDto>,
)
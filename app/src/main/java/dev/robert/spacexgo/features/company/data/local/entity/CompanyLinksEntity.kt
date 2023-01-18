package dev.robert.spacexgo.features.company.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

data class CompanyLinksEntity(
    val elonTwitter: String,
    val flickr: String,
    val twitter: String,
    val website: String
)

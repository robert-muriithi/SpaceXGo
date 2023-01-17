package dev.robert.spacexgo.features.company.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyLinksEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val elonTwitter: String,
    val flickr: String,
    val twitter: String,
    val website: String
)

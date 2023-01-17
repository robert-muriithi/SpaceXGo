package dev.robert.spacexgo.features.company.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.robert.spacexgo.core.utils.Constants
import dev.robert.spacexgo.features.company.domain.model.CompanyLinks


@Entity(tableName = Constants.COMPANY_INFO_TABLE)
data class CompanyInfoEntity(
    val ceo: String,
    val coo: String,
    val cto: String,
    val ctoPropulsion: String,
    val employees: Int,
    val founded: Int,
    val founder: String,
    val headquarters: HeadquarterEntity,
    @PrimaryKey val id: String,
    val launchSites: Int,
    val links: CompanyLinksEntity,
    val name: String,
    val summary: String,
    val testSites: Int,
    val valuation: Long,
    val vehicles: Int
)

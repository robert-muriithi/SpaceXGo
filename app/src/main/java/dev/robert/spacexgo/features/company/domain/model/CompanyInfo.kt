package dev.robert.spacexgo.features.company.domain.model

import com.google.gson.annotations.SerializedName
import dev.robert.spacexgo.core.data.dto.CompanyLinksDto
import dev.robert.spacexgo.core.data.dto.HeadquartersDto

data class CompanyInfo(
    val ceo: String,
    val coo: String,
    val cto: String,
    val ctoPropulsion: String,
    val employees: Int,
    val founded: Int,
    val founder: String,
    val headquarters: Headquarters,
    val id: String,
    val launchSites: Int,
    val links: CompanyLinks,
    val name: String,
    val summary: String,
    val testSites: Int,
    val valuation: Long,
    val vehicles: Int
)

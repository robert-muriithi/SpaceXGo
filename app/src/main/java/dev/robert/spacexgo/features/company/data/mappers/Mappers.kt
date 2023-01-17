package dev.robert.spacexgo.features.company.data.mappers

import dev.robert.spacexgo.core.data.dto.CompanyInfoDto
import dev.robert.spacexgo.core.data.dto.CompanyLinksDto
import dev.robert.spacexgo.core.data.dto.HeadquartersDto
import dev.robert.spacexgo.features.company.data.local.entity.CompanyInfoEntity
import dev.robert.spacexgo.features.company.data.local.entity.CompanyLinksEntity
import dev.robert.spacexgo.features.company.data.local.entity.HeadquarterEntity
import dev.robert.spacexgo.features.company.domain.model.CompanyInfo
import dev.robert.spacexgo.features.company.domain.model.CompanyLinks
import dev.robert.spacexgo.features.company.domain.model.Headquarters

fun CompanyInfoDto.toCompanyEntity() : CompanyInfoEntity {
    return CompanyInfoEntity(
        ceo = ceo,
        coo = coo,
         cto = cto,
        ctoPropulsion = ctoPropulsion,
        employees = employees,
        founded = founded,
        founder = founder,
        headquarters = headquarters.toHeadQuarterEntity(),
        id = id,
        launchSites = launchSites,
        links =  links.toCompanyEntity(),
        name = name,
        summary = summary,
        testSites = testSites,
        valuation = valuation,
        vehicles = vehicles
    )
}
fun CompanyInfoEntity.toCompany() : CompanyInfo {
    return CompanyInfo(
        ceo = ceo,
        coo = coo,
        cto = cto,
        ctoPropulsion = ctoPropulsion,
        employees = employees,
        founded = founded,
        founder = founder,
        headquarters = headquarters.toHeadQuarter(),
        id = id,
        launchSites = launchSites,
        links =  links.toCompanyLinks(),
        name = name,
        summary = summary,
        testSites = testSites,
        valuation = valuation,
        vehicles = vehicles
    )
}

fun HeadquartersDto.toHeadQuarterEntity() : HeadquarterEntity {
    return HeadquarterEntity(
        address = address,
        city = city,
        state = state
    )
}

fun HeadquarterEntity.toHeadQuarter() : Headquarters {
    return Headquarters(
        address = address,
        city = city,
        state = state
    )
}

fun CompanyLinksDto.toCompanyEntity() : CompanyLinksEntity {
    return CompanyLinksEntity(
        elonTwitter = elonTwitter,
        flickr = flickr,
        twitter =  twitter,
        website = website,
    )

}

fun CompanyLinksEntity.toCompanyLinks() : CompanyLinks{
    return CompanyLinks(
        elonTwitter = elonTwitter,
        flickr = flickr,
        twitter = twitter,
        website = website
    )
}
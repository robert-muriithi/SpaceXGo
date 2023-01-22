package dev.robert.spacexgo.features.company.data.mappers

import dev.robert.spacexgo.core.data.dto.*
import dev.robert.spacexgo.features.company.data.local.entity.*
import dev.robert.spacexgo.features.company.domain.model.*

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

fun HistoryDto.toHistoryEntity() : HistoryEntity {
    return HistoryEntity(
        details = details,
        eventDateUtc = eventDateUtc,
        id = id,
        links = links.toHistoryEntity(),
        title = title
    )
}

fun HistoryEntity.toHistory() : History {
    return History(
        details = details,
        eventDateUtc = eventDateUtc,
        id = id,
        links = links.toHistoryLinks(),
        title = title
    )
}

fun HistoryLinksDto.toHistoryEntity() : HistoryLinksEntity {
    return HistoryLinksEntity(
        article = article,
    )
}

fun HistoryLinksEntity.toHistoryLinks() : HistoryLinks {
    return HistoryLinks(
        article = article,
    )
}

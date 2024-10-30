package dev.robert.spacexgo.features.launches.data.mappers

import dev.robert.spacexgo.core.data.dto.LaunchesDto
import dev.robert.spacexgo.features.launches.data.local.entity.LaunchesEntity
import dev.robert.spacexgo.features.launches.domain.model.Launches


fun LaunchesDto.toLaunchesEntity() : LaunchesEntity {
    return LaunchesEntity(
        autoUpdate = autoUpdate,
        capsules = capsules,
        dateLocal = dateLocal,
        datePrecision = datePrecision,
        dateUtc = dateUtc,
        details = details,
        flightNumber = flightNumber,
        id = id,
        launchLibraryId = launchLibraryId,
        launchpad = launchpad,
        linksDto = linksDto,
        name = name,
        net = net,
        payloads = payloads,
        rocket = rocket,
        ships = ships,
        success = success,
        upcoming = upcoming,
        crew = crew,
    )
}

fun LaunchesEntity.toLaunches() : Launches {
    return Launches(
        autoUpdate = autoUpdate,
        capsules = capsules,
        dateLocal = dateLocal,
        datePrecision = datePrecision,
        dateUtc = dateUtc,
        details = details,
        flightNumber = flightNumber,
        id = id,
        launchLibraryId = launchLibraryId,
        launchpad = launchpad,
        linksDto = linksDto,
        name = name,
        net = net,
        payloads = payloads,
        rocket = rocket,
        ships = ships ?: emptyList(),
        success = success,
        upcoming = upcoming,
        crew = crew,
    )
}


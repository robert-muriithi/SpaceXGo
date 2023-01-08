package dev.robert.spacexgo.features.capsules.data.mappers

import dev.robert.spacexgo.core.data.dto.CapsulesDto
import dev.robert.spacexgo.features.capsules.data.local.entity.CapsulesEntity
import dev.robert.spacexgo.features.capsules.domain.model.Capsule

fun CapsulesDto.toCapsuleEntity() : CapsulesEntity{
    return CapsulesEntity(
        id = id,
        landLandings = landLandings,
        lastUpdate = lastUpdate,
        launches = launches,
        reuseCount = reuseCount,
        serial = serial,
        status = status,
        type = type,
        waterLandings = waterLandings
    )
}

fun CapsulesEntity.toCapsule() : Capsule {
    return Capsule(
        id = id,
        landLandings = landLandings,
        lastUpdate = lastUpdate,
        launches = launches,
        reuseCount = reuseCount,
        serial = serial,
        status = status,
        type = type,
        waterLandings = waterLandings
    )
}
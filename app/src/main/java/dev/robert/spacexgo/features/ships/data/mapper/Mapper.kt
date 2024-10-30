package dev.robert.spacexgo.features.ships.data.mapper

import dev.robert.spacexgo.core.data.dto.ShipsDto
import dev.robert.spacexgo.features.ships.data.local.entity.ShipEntity
import dev.robert.spacexgo.features.ships.domain.model.Ship

fun ShipEntity.toShip(): Ship {
    return Ship(
        active = active,
        id = id,
        image = shipImage,
        lastAisUpdate = lastAisUpdate,
        launches = launches ?: emptyList(),
        massKg = massKg,
        model = model,
        name = name,
        roles = roles ?: emptyList(),
        speedKn = speedKn,
        status = status,
        type = type,
        yearBuilt = yearBuilt,
        homePort = homePort,
        legacyId = legacyId,
        link = link
    )
}

fun ShipsDto.toShipEntity(): ShipEntity {
    return ShipEntity(
        active = active,
        id = id,
        shipImage = image,
        lastAisUpdate = lastAisUpdate,
        launches = launches,
        massKg = massKg,
        model = model,
        name = name,
        roles = roles,
        speedKn = speedKn,
        status = status,
        type = type,
        yearBuilt = yearBuilt,
        homePort = homePort,
        legacyId = legacyId,
        link = link,
    )
}
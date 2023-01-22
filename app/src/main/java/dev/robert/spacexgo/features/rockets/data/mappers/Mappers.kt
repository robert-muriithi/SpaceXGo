package dev.robert.spacexgo.features.rockets.data.mappers

import dev.robert.spacexgo.core.data.dto.RocketsDto
import dev.robert.spacexgo.features.rockets.data.local.entity.RocketEntity
import dev.robert.spacexgo.features.rockets.domain.model.Rocket

fun RocketsDto.toRocketsEntity() : RocketEntity{
    return RocketEntity(
        active = active,
        country = country,
        description = description,
        flickrImages = flickrImages,
        id = id,
        name = name,
        stages = stages,
        successRatePct = successRatePct,
        type = type
    )
}

fun RocketEntity.toRocket() : Rocket{
    return Rocket(
        active = active,
        country = country,
        description = description,
        flickrImages = flickrImages,
        id = id,
        name = name,
        stages = stages,
        successRatePct = successRatePct,
        type = type
    )
}
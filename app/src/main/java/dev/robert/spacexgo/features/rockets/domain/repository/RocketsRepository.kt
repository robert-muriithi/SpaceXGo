package dev.robert.spacexgo.features.rockets.domain.repository

import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.rockets.domain.model.Rocket
import kotlinx.coroutines.flow.Flow

interface RocketsRepository {
    fun getAllRockets() : Flow<Resource<List<Rocket>>>
    fun getSingleRocket(id: String) : Flow<Resource<Rocket>>
}
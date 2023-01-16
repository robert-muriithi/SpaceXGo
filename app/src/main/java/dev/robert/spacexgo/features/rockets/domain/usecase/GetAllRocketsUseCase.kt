package dev.robert.spacexgo.features.rockets.domain.usecase

import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.rockets.domain.model.Rocket
import dev.robert.spacexgo.features.rockets.domain.repository.RocketsRepository
import kotlinx.coroutines.flow.Flow

class GetAllRocketsUseCase(private val repository: RocketsRepository) {
    operator fun invoke(): Flow<Resource<List<Rocket>>> {
        return repository.getAllRockets()
    }
}
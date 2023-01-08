package dev.robert.spacexgo.features.capsules.domain.usecase

import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.capsules.domain.model.Capsule
import dev.robert.spacexgo.features.capsules.domain.repository.CapsulesRepository
import kotlinx.coroutines.flow.Flow

class GetAllCapsulesUseCase(private val repository: CapsulesRepository) {
    operator fun invoke() : Flow<Resource<List<Capsule>>> {
        return repository.getAllCapsules()
    }
}
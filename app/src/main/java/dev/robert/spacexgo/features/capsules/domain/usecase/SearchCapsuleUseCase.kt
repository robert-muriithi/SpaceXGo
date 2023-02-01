package dev.robert.spacexgo.features.capsules.domain.usecase

import dev.robert.spacexgo.features.capsules.domain.repository.CapsulesRepository

class SearchCapsuleUseCase (private val repository: CapsulesRepository) {
    operator fun invoke(query: String) = repository.searchCapsules(query)
}
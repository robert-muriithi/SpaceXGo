package dev.robert.spacexgo.features.launches.domain.usecase

import dev.robert.spacexgo.features.launches.domain.repository.LaunchesRepository

class SearchLaunchesUseCase (private val launchesRepository: LaunchesRepository) {
    operator fun invoke(query: String) = launchesRepository.searchLaunches(query)
}

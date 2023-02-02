package dev.robert.spacexgo.features.launches.domain.usecase

import dev.robert.spacexgo.features.launches.domain.repository.LaunchesRepository

class GetSingleLaunchUseCase (private val launchesRepository: LaunchesRepository) {
    operator fun invoke(id: String)  = launchesRepository.getLaunchById(id)
}
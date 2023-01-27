package dev.robert.spacexgo.features.launches.domain.usecase

import dev.robert.spacexgo.features.launches.domain.repository.LaunchesRepository


class GetAllLaunchesUseCase(private val launchesRepository: LaunchesRepository) {
    operator fun invoke() = launchesRepository.getLaunches()
}
package dev.robert.spacexgo.features.launches.domain.usecase

import dev.robert.spacexgo.features.launches.domain.repository.LaunchesRepository

class GetPastLaunchesUseCase(private val launchesRepository: LaunchesRepository) {

    operator fun invoke() = launchesRepository.getPastLaunches()

}
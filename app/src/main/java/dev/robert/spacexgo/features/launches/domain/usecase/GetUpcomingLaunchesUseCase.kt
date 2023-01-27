package dev.robert.spacexgo.features.launches.domain.usecase

import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.launches.domain.model.Launches
import dev.robert.spacexgo.features.launches.domain.repository.LaunchesRepository
import kotlinx.coroutines.flow.Flow

class GetUpcomingLaunchesUseCase (private val launchesRepository: LaunchesRepository) {
    operator fun invoke() = launchesRepository.getUpcomingLaunches()
}
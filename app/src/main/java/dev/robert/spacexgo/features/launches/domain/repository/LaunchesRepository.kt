package dev.robert.spacexgo.features.launches.domain.repository

import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.launches.domain.model.Launches
import kotlinx.coroutines.flow.Flow

interface LaunchesRepository {
    fun getLaunches(): Flow<Resource<List<Launches>>>
    fun getLaunchById(id: String): Flow<Resource<Launches>>
    fun getPastLaunches(): Flow<Resource<List<Launches>>>
    fun getUpcomingLaunches(): Flow<Resource<List<Launches>>>
}
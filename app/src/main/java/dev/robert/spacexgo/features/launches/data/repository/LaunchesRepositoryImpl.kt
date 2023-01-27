package dev.robert.spacexgo.features.launches.data.repository

import dev.robert.spacexgo.core.data.local.database.SpaceXDatabase
import dev.robert.spacexgo.core.data.remote.ApiService
import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.launches.data.mappers.toLaunches
import dev.robert.spacexgo.features.launches.data.mappers.toLaunchesEntity
import dev.robert.spacexgo.features.launches.domain.model.Launches
import dev.robert.spacexgo.features.launches.domain.repository.LaunchesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class LaunchesRepositoryImpl(
    private val api: ApiService,
    private val db: SpaceXDatabase,
) : LaunchesRepository {

    override fun getLaunches(): Flow<Resource<List<Launches>>> = flow {

        val savedLaunches = db.launchesDao.getLaunches()
        if (savedLaunches.isNotEmpty()) {
            emit(Resource.Success(savedLaunches.map { it.toLaunches() }))
        } else {
            try {
                val response = api.getAllLaunches()
                db.launchesDao.deleteLaunches()
                db.launchesDao.insertLaunches(response.map { it.toLaunchesEntity() })
                val localLaunches = db.launchesDao.getLaunches()
                emit(Resource.Success(localLaunches.map { it.toLaunches() }))
            } catch (e: HttpException) {
                emit(Resource.Error("Unknown error occurred"))

            } catch (e: IOException) {
                emit(Resource.Error("Couldn't connect to the server. Please check your internet connection."))
            }
        }

    }.flowOn(Dispatchers.IO)

    override fun getLaunchById(id: String): Flow<Resource<Launches>> = flow {
        val savedLaunch = db.launchesDao.getLaunchById(id)
        emit(Resource.Success(savedLaunch.toLaunches()))
    }.flowOn(Dispatchers.IO)

    override fun getPastLaunches(): Flow<Resource<List<Launches>>> =
        flow<Resource<List<Launches>>> {
            val pastLaunches = db.launchesDao.getPastLaunches()
            emit(Resource.Success(pastLaunches.map { it.toLaunches() }))
        }.flowOn(Dispatchers.IO)

    override fun getUpcomingLaunches(): Flow<Resource<List<Launches>>> =
        flow<Resource<List<Launches>>> {
            val savedLaunches = db.launchesDao.getUpcomingLaunches()
            emit(Resource.Success(savedLaunches.map { it.toLaunches() }))
        }.flowOn(Dispatchers.IO)
}
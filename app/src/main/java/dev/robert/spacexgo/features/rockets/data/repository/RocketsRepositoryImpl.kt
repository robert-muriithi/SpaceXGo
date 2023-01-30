package dev.robert.spacexgo.features.rockets.data.repository

import dev.robert.spacexgo.core.data.remote.ApiService
import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.rockets.data.local.dao.RocketsDao
import dev.robert.spacexgo.features.rockets.data.mappers.toRocket
import dev.robert.spacexgo.features.rockets.data.mappers.toRocketsEntity
import dev.robert.spacexgo.features.rockets.domain.model.Rocket
import dev.robert.spacexgo.features.rockets.domain.repository.RocketsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class RocketsRepositoryImpl(
    private val api: ApiService,
    private val dao: RocketsDao,
) : RocketsRepository {

    override fun getAllRockets(): Flow<Resource<List<Rocket>>> = flow {
        emit(Resource.Loading())
        val dbRockets = dao.getAllRockets()
        if (dbRockets.isNotEmpty()){
            emit(Resource.Success(data = dbRockets.map { it.toRocket() }))
        }else{
            try {
                val remoteRockets = api.getAllRockets()
                dao.deleteRockets()
                dao.insertRockets(remoteRockets.map { it.toRocketsEntity() })
                val rockets = dao.getAllRockets()
                emit(Resource.Success(rockets.map { it.toRocket() }))
            }catch (e: HttpException){
                emit(Resource.Error("Unknown error occurred"))

            }catch (e: IOException){
                emit(Resource.Error("Couldn't connect to the server. Please check your internet connection."))

            }
        }
    }.flowOn(Dispatchers.IO)
}
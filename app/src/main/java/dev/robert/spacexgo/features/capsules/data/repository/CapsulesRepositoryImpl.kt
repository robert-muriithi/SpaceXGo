package dev.robert.spacexgo.features.capsules.data.repository

import dev.robert.spacexgo.core.data.remote.ApiService
import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.capsules.data.local.dao.CapsulesDao
import dev.robert.spacexgo.features.capsules.data.mappers.toCapsule
import dev.robert.spacexgo.features.capsules.data.mappers.toCapsuleEntity
import dev.robert.spacexgo.features.capsules.domain.model.Capsule
import dev.robert.spacexgo.features.capsules.domain.repository.CapsulesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException

class CapsulesRepositoryImpl(private val api: ApiService, private val dao: CapsulesDao) : CapsulesRepository {

    override fun getAllCapsules(): Flow<Resource<List<Capsule>>> = flow {

        val localCapsules = dao.getAllCapsules()
        if(localCapsules.isNotEmpty()){
            emit(Resource.Success(localCapsules.map { it.toCapsule() }))
        }else{
            try {
                val remoteCapsules = api.getAllCapsules()
                dao.deleteAllCapsules()
                dao.insertAllCapsules(remoteCapsules.map { it.toCapsuleEntity() })
                val results = dao.getAllCapsules()
                emit(Resource.Success(results.map { it.toCapsule() }))

            }catch (e: HttpException) {
                emit(Resource.Error("Unknown error occurred"))

            } catch (e: IOException) {
                emit(Resource.Error("Couldn't connect to the server. Please check your internet connection."))
            }

        }
    }.flowOn(Dispatchers.IO)

    override fun searchCapsules(query: String): Flow<Resource<List<Capsule>>> = flow {
        val results = dao.searchCapsules(query)
        emit(Resource.Success(results.map { it.toCapsule() }))
    }.flowOn(Dispatchers.IO)

}
package dev.robert.spacexgo.features.ships.data.repository

import dev.robert.spacexgo.core.data.remote.ApiService
import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.ships.data.local.dao.ShipDao
import dev.robert.spacexgo.features.ships.data.mapper.toShip
import dev.robert.spacexgo.features.ships.data.mapper.toShipEntity
import dev.robert.spacexgo.features.ships.domain.model.Ship
import dev.robert.spacexgo.features.ships.domain.repository.ShipsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException


class ShipsRepositoryImpl(private val api: ApiService, private val dao: ShipDao) : ShipsRepository {

    override fun getAllShips(): Flow<Resource<List<Ship>>> = flow {
        //Query data from room first, check if data exists
        val resultFromDb = dao.getAllShips()
        if (resultFromDb.isNotEmpty()) {
            emit(Resource.Success(resultFromDb.map { it.toShip() }))
        } else {
            try {
                val response = api.getAllShips()
                dao.deleteAllShips()
                dao.insertShips(ship = response.map { it.toShipEntity() })
                val shipsFromDb = dao.getAllShips()
                emit(Resource.Success(shipsFromDb.map { it.toShip() }))
            } catch (e: HttpException) {
                emit(Resource.Error("Unknown error occurred"))

            } catch (e: IOException) {
                emit(Resource.Error("Couldn't connect to the server. Please check your internet connection."))
            }

        }
    }.flowOn(Dispatchers.IO)


    override fun getShipById(id: String): Flow<Resource<Ship>> {
        return flow {
            val resultFromDb = dao.getShipById(id)
            emit(Resource.Success(resultFromDb.toShip()))
        }.flowOn(Dispatchers.IO)
    }
}
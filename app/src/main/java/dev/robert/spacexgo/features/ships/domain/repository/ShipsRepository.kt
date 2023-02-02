package dev.robert.spacexgo.features.ships.domain.repository

import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.ships.domain.model.Ship
import kotlinx.coroutines.flow.Flow

interface ShipsRepository {
    fun getAllShips() : Flow<Resource<List<Ship>>>
    fun getShipById(id: String) : Flow<Resource<Ship>>
}
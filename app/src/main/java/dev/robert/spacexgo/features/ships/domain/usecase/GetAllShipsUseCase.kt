package dev.robert.spacexgo.features.ships.domain.usecase

import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.ships.domain.model.Ship
import dev.robert.spacexgo.features.ships.domain.repository.ShipsRepository
import kotlinx.coroutines.flow.Flow

class GetAllShipsUseCase(private val shipsRepository: ShipsRepository) {
    operator fun invoke() : Flow<Resource<List<Ship>>> {
        return shipsRepository.getAllShips()
    }

}
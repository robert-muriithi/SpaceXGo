package dev.robert.spacexgo.features.ships.domain.usecase

import dev.robert.spacexgo.features.ships.domain.repository.ShipsRepository

class GetShipUseCase (private val shipsRepository: ShipsRepository) {
    operator fun invoke(id: String) = shipsRepository.getShipById(id)
}

package dev.robert.spacexgo.features.capsules.domain.repository

import dev.robert.spacexgo.core.utils.Resource
import dev.robert.spacexgo.features.capsules.domain.model.Capsule
import kotlinx.coroutines.flow.Flow

interface CapsulesRepository {
    fun getAllCapsules() : Flow<Resource<List<Capsule>>>
    fun searchCapsules(query: String) : Flow<Resource<List<Capsule>>>
}
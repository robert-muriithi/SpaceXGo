package dev.robert.spacexgo.core.data.remote

import dev.robert.spacexgo.core.data.dto.CapsulesDto
import dev.robert.spacexgo.core.data.dto.ShipsDto
import retrofit2.http.GET

interface ApiService {
    @GET("v4/ships")
    suspend fun getAllShips() : List<ShipsDto>

    @GET("v4/capsules")
    suspend fun getAllCapsules() : List<CapsulesDto>

}
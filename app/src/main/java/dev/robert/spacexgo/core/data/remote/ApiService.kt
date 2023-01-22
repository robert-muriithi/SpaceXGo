package dev.robert.spacexgo.core.data.remote

import dev.robert.spacexgo.core.data.dto.*
import retrofit2.http.GET

interface ApiService {
    @GET("v4/ships")
    suspend fun getAllShips() : List<ShipsDto>

    @GET("v4/capsules")
    suspend fun getAllCapsules() : List<CapsulesDto>

    @GET("v4/rockets")
    suspend fun getAllRockets() : List<RocketsDto>

    @GET("v4/company")
    suspend fun getCompanyInfo() : CompanyInfoDto

    @GET("v5/launches")
    suspend fun getAllLaunches() : List<LaunchesDto>

    @GET("v5/launches/latest")
    suspend fun getLatestLaunches() : List<LatestLaunchesDto>

    @GET("v4/history")
    suspend fun getCompanyHistory() : List<HistoryDto>

}
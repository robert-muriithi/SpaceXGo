package dev.robert.spacexgo.features.rockets.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.robert.spacexgo.core.data.remote.ApiService
import dev.robert.spacexgo.features.rockets.data.local.dao.RocketsDao
import dev.robert.spacexgo.features.rockets.data.repository.RocketsRepositoryImpl
import dev.robert.spacexgo.features.rockets.domain.repository.RocketsRepository
import dev.robert.spacexgo.features.rockets.domain.usecase.GetAllRocketsUseCase
import dev.robert.spacexgo.features.rockets.domain.usecase.GetSingleRocketUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RocketModule {


    @Provides
    @Singleton
    fun provideRocketsRepository(
        apiService: ApiService,
        dao: RocketsDao,
    ): RocketsRepository {
        return RocketsRepositoryImpl(apiService, dao)
    }


    @Provides
    @Singleton
    fun provideRocketsUseCase(repository: RocketsRepository): GetAllRocketsUseCase {
        return GetAllRocketsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetSingleRocketUseCase(repository: RocketsRepository) : GetSingleRocketUseCase{
        return GetSingleRocketUseCase(repository)
    }
}
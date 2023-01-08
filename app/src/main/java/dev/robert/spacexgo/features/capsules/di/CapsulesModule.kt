package dev.robert.spacexgo.features.capsules.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.robert.spacexgo.core.data.remote.ApiService
import dev.robert.spacexgo.features.capsules.data.local.dao.CapsulesDao
import dev.robert.spacexgo.features.capsules.data.remote.CapsulesRepositoryImpl
import dev.robert.spacexgo.features.capsules.domain.repository.CapsulesRepository
import dev.robert.spacexgo.features.capsules.domain.usecase.GetAllCapsulesUseCase
import dev.robert.spacexgo.features.ships.domain.usecase.GetAllShipsUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CapsulesModule {

    @Provides
    @Singleton
    fun provideCapsulesRepository(
        api: ApiService,
        capsulesDao: CapsulesDao
    ) : CapsulesRepository{
        return CapsulesRepositoryImpl(
            api = api,
            dao = capsulesDao
        )
    }

    @Provides
    @Singleton
    fun provideCapsuleUseCase(
        repository: CapsulesRepository
    ) : GetAllCapsulesUseCase{
        return GetAllCapsulesUseCase(
            repository = repository
        )
    }
}
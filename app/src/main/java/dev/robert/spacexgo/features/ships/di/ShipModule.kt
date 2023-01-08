package dev.robert.spacexgo.features.ships.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.robert.spacexgo.core.data.remote.ApiService
import dev.robert.spacexgo.features.ships.data.local.dao.ShipDao
import dev.robert.spacexgo.features.ships.data.repository.ShipsRepositoryImpl
import dev.robert.spacexgo.features.ships.domain.repository.ShipsRepository
import dev.robert.spacexgo.features.ships.domain.usecase.GetAllShipsUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ShipModule {
    @Provides
    @Singleton
    fun provideShipsRepository(
        api : ApiService,
        dao: ShipDao
    ) : ShipsRepository{
        return ShipsRepositoryImpl(
            api = api,
            dao = dao
        )
    }

    @Provides
    @Singleton
    fun provideGetAllShipsUseCase(
        repository: ShipsRepository
    ) : GetAllShipsUseCase{
        return GetAllShipsUseCase(
            repository
        )
    }
}
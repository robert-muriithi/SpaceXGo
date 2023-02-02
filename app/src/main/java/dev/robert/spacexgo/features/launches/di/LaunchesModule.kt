package dev.robert.spacexgo.features.launches.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.robert.spacexgo.core.data.local.database.SpaceXDatabase
import dev.robert.spacexgo.core.data.remote.ApiService
import dev.robert.spacexgo.features.launches.data.repository.LaunchesRepositoryImpl
import dev.robert.spacexgo.features.launches.domain.repository.LaunchesRepository
import dev.robert.spacexgo.features.launches.domain.usecase.*
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LaunchesModule {

    @Provides
    @Singleton
    fun provideLaunchesRepository(
        api: ApiService,
        db: SpaceXDatabase,
    ): LaunchesRepository {
        return LaunchesRepositoryImpl(
            api = api,
            db = db,
        )
    }

    @Provides
    @Singleton
    fun provideGetAllLaunchesUseCase(
        launchesRepository: LaunchesRepository
    ): GetAllLaunchesUseCase {
        return GetAllLaunchesUseCase(launchesRepository = launchesRepository)
    }

    @Provides
    @Singleton
    fun provideGetSingleLaunchUseCase(
        launchesRepository: LaunchesRepository
    ): GetSingleLaunchUseCase {
        return GetSingleLaunchUseCase(launchesRepository = launchesRepository)
    }

    @Provides
    @Singleton
    fun provideGetPastLaunchesUseCase(
        launchesRepository: LaunchesRepository
    ): GetPastLaunchesUseCase {
        return GetPastLaunchesUseCase(launchesRepository = launchesRepository)
    }

    @Provides
    @Singleton
    fun provideGetUpcomingLaunchesUseCase(
        launchesRepository: LaunchesRepository
    ): GetUpcomingLaunchesUseCase {
        return GetUpcomingLaunchesUseCase(launchesRepository = launchesRepository)
    }

    @Provides
    @Singleton
    fun provideSearchLaunchesUseCase(
        launchesRepository: LaunchesRepository
    ): SearchLaunchesUseCase {
        return SearchLaunchesUseCase(launchesRepository = launchesRepository)
    }

}
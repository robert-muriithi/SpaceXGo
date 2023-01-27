package dev.robert.spacexgo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/*
@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Singleton
    fun providesDefaultDispatcher() : CoroutineDispatcher {
        return Dispatchers.Default
    }

    @Provides
    @Singleton
    fun providesIODispatcher() : CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun providesMainDispatcher() : CoroutineDispatcher {
        return Dispatchers.Main
    }

    @Provides
    @Singleton
    fun providesUnconfinedDispatcher() : CoroutineDispatcher {
        return Dispatchers.Unconfined
    }
}*/

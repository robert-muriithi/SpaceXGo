package dev.robert.spacexgo.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.robert.spacexgo.core.data.local.database.SpaceXDatabase
import dev.robert.spacexgo.core.utils.Constants
import dev.robert.spacexgo.features.capsules.data.local.dao.CapsulesDao
import dev.robert.spacexgo.features.ships.data.converters.Converters
import dev.robert.spacexgo.features.ships.data.local.dao.ShipDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideConverters(gson: Gson) : Converters{
        return Converters(gson = gson)
    }

    @Provides
    @Singleton
    fun provideSpaceXDatabase(@ApplicationContext context: Context, converters: Converters) : SpaceXDatabase{
        return Room.databaseBuilder(
            context,
            SpaceXDatabase::class.java,
            Constants.SPACE_X_DB
        )
            .addTypeConverter(converters)
            .build()

    }

    @Provides
    @Singleton
    fun provideDao (db : SpaceXDatabase) : ShipDao{
        return db.shipDao
    }

    @Provides
    @Singleton
    fun provideCapsulesDao(db: SpaceXDatabase): CapsulesDao{
        return db.capsulesDao
    }

}
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
import dev.robert.spacexgo.features.company.data.local.dao.CompanyInfoDao
import dev.robert.spacexgo.features.company.data.local.dao.HistoryDao
import dev.robert.spacexgo.features.launches.data.local.dao.LaunchesDao
import dev.robert.spacexgo.features.rockets.data.local.dao.RocketsDao
import dev.robert.spacexgo.core.data.converters.Converters
import dev.robert.spacexgo.features.ships.data.local.dao.ShipDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideConverters(gson: Gson) : Converters {
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

    @Provides
    @Singleton
    fun provideRocketsDao(db: SpaceXDatabase) : RocketsDao {
        return db.rocketsDao
    }

    @Provides
    @Singleton
    fun provideCompanyInfoDao(db: SpaceXDatabase) : CompanyInfoDao {
        return db.companyInfoDao
    }
    @Provides
    @Singleton
    fun provideHistoryDao(db: SpaceXDatabase) : HistoryDao {
        return db.historyDao
    }

    @Provides
    @Singleton
    fun provideLaunchesDao(db: SpaceXDatabase) : LaunchesDao {
        return db.launchesDao
    }
}
package dev.robert.spacexgo.features.launches.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.robert.spacexgo.features.launches.data.local.entity.LaunchesEntity

@Dao
interface LaunchesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLaunches(launches: List<LaunchesEntity>)

    @Query("SELECT * FROM launches_table")
    fun getLaunches(): List<LaunchesEntity>

    @Query("DELETE FROM launches_table")
    suspend fun deleteLaunches()

    @Query("SELECT * FROM launches_table WHERE id = :id")
    fun getLaunchById(id: String): LaunchesEntity

    @Query("SELECT * FROM launches_table WHERE upcoming = 1")
    fun getUpcomingLaunches(): List<LaunchesEntity>

    @Query("SELECT * FROM launches_table WHERE upcoming = 0")
    fun getPastLaunches(): List<LaunchesEntity>

    @Query("SELECT * FROM launches_table WHERE name LIKE '%' || :query || '%'")
    fun searchLaunches(query: String): List<LaunchesEntity>

}
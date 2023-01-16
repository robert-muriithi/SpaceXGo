package dev.robert.spacexgo.features.rockets.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.robert.spacexgo.features.rockets.data.local.entity.RocketEntity

@Dao
interface RocketsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRockets(rockets: List<RocketEntity>)

    @Query("DELETE  FROM rockets_table")
    suspend fun deleteRockets()

    @Query("SELECT * FROM rockets_table")
    fun getAllRockets() : List<RocketEntity>

}
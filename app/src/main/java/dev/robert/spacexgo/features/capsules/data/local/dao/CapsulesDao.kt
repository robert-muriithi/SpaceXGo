package dev.robert.spacexgo.features.capsules.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.robert.spacexgo.features.capsules.data.local.entity.CapsulesEntity

@Dao
interface CapsulesDao {
    @Insert
    suspend fun insertAllCapsules(capsules : List<CapsulesEntity>)

    @Query("SELECT * FROM capsules_table")
    fun getAllCapsules() : List<CapsulesEntity>

    @Query("DELETE FROM capsules_table")
    suspend fun deleteAllCapsules()
}
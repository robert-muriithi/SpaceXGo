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

    //Search by  capsule serial, capsule status, capsule type
    @Query("SELECT * FROM capsules_table WHERE serial LIKE :query OR status LIKE :query OR type LIKE :query")
    fun searchCapsules(query: String) : List<CapsulesEntity>
}
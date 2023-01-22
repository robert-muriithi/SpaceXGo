package dev.robert.spacexgo.features.company.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.robert.spacexgo.features.company.data.local.entity.HistoryEntity

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyHistory(info: List<HistoryEntity>)

    @Query("DELETE FROM history_table")
    suspend fun deleteCompanyHistory()

    @Query("SELECT * FROM history_table")
    fun getCompanyHistory() : List<HistoryEntity>
}
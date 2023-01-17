package dev.robert.spacexgo.features.company.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.robert.spacexgo.features.company.data.local.entity.CompanyInfoEntity

@Dao
interface CompanyInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyInfo(info: CompanyInfoEntity)

    @Query("DELETE FROM company_info_table")
    suspend fun deleteCompanyInfo()

    @Query("SELECT * FROM company_info_table")
    fun getCompanyInfo() : CompanyInfoEntity
}
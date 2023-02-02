package dev.robert.spacexgo.features.ships.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.robert.spacexgo.features.ships.data.local.entity.ShipEntity


@Dao
interface ShipDao {
   @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insertShips(ship: List<ShipEntity>)

   @Query("DELETE FROM ships_table")
   suspend fun deleteAllShips()

   @Query("SELECT * FROM ships_table")
   fun getAllShips() : List<ShipEntity>

   @Query("SELECT * FROM ships_table WHERE id = :id")
   fun getShipById(id: String) : ShipEntity
}
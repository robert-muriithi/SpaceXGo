package dev.robert.spacexgo.core.data.local.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import dev.robert.spacexgo.core.utils.Constants
import dev.robert.spacexgo.features.ships.data.converters.Converters
import dev.robert.spacexgo.features.ships.data.local.dao.ShipDao
import dev.robert.spacexgo.features.ships.data.local.entity.ShipEntity

@Database(
    entities = [ShipEntity::class],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = SpaceXDatabase.Migration1To2::class)
    ],
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class SpaceXDatabase : RoomDatabase() {
    abstract val shipDao: ShipDao

    @RenameColumn(
        tableName = Constants.SHIP_TABLE,
        fromColumnName = "image",
        toColumnName = "shipImage"
    )
    class Migration1To2 : AutoMigrationSpec
}
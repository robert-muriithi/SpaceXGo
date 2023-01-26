package dev.robert.spacexgo.features.ships.data.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dev.robert.spacexgo.features.company.data.local.entity.CompanyLinksEntity
import dev.robert.spacexgo.features.company.data.local.entity.HeadquarterEntity
import dev.robert.spacexgo.features.company.data.local.entity.HistoryLinksEntity

@ProvidedTypeConverter
class Converters(
    private val gson : Gson
) {

    @TypeConverter
    fun fromJson(json: String): List<String> {
        return gson.fromJson<ArrayList<String>>(
            json,
            object : TypeToken<ArrayList<String>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toJson(roles: List<String>): String {
        return gson.toJson(
            roles,
            object : TypeToken<ArrayList<String>>() {}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromHeadQuarters(headquarterEntity: HeadquarterEntity) : String{
        return gson.toJson(headquarterEntity)
    }
    @TypeConverter
    fun toHeadQuarters(json: String) : HeadquarterEntity {
        return gson.fromJson(json, HeadquarterEntity::class.java)
    }

    @TypeConverter
    fun fromCompanyLinks(companyLinksEntity: CompanyLinksEntity) : String{
        return gson.toJson(companyLinksEntity)
    }

    @TypeConverter
    fun toCompanyLinks(json: String) : CompanyLinksEntity {
        return gson.fromJson(json, CompanyLinksEntity::class.java)
    }

    @TypeConverter
    fun fromHistoryLinks(historyLinksEntity: HistoryLinksEntity) : String{
        return gson.toJson(historyLinksEntity)
    }
    @TypeConverter
    fun toHistoryLinks(json: String) : HistoryLinksEntity {
        return gson.fromJson(json, HistoryLinksEntity::class.java)
    }
}
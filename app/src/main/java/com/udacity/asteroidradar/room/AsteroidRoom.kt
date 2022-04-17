package com.udacity.asteroidradar.room

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * https://developer.android.com/training/data-storage/room/referencing-data
 * */

//class Converters {
//
//    @SuppressLint("NewApi")
//    @TypeConverter
//    fun fromTimeStamp(value: Timestamp?) : LocalDate? {
//        value ?: return null
//        return value
//    }
//
//    @SuppressLint("NewApi")
//    @TypeConverter
//    fun toTimeStamp(value: LocalDate?) : String? {
//        value ?: return null
//
//    }
//}

@Entity(tableName = "asteroid_table")
data class AsteroidRoom (
    @PrimaryKey(autoGenerate = true)
    val id : Long,

    @ColumnInfo(name = "code_name")
    val codeName : String,

    @ColumnInfo(name = "close_approach_date")
    val closeApproachDate: Long,

    @ColumnInfo(name="absolute_magnitude")
    val absoluteMagnitude: Double,

    @ColumnInfo(name = "estimated_diameter")
    val estimatedDiameter: Double,

    @ColumnInfo(name = "relative_velocity")
    val relativeVelocity: Double,

    @ColumnInfo(name = "distance_from_earth")
    val distanceFromEarth: Double,

    @ColumnInfo(name = "is_potentially_hazardous")
    val isPotentiallyHazardous: Boolean
)

@SuppressLint("NewApi")
fun List<AsteroidRoom>.asDomainData() : List<Asteroid> {
    return map {
        val calendar : Calendar = Calendar.getInstance()
        calendar.timeInMillis = it.closeApproachDate
        val dateFormat = SimpleDateFormat(Constants.DATABASE_FORMAT_ENTRY)
        Asteroid(
            id = it.id,
            codename = it.codeName,
            closeApproachDate = dateFormat.format(calendar.time),
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}
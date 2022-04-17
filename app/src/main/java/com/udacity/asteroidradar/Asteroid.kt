package com.udacity.asteroidradar

import android.annotation.SuppressLint
import android.os.Parcelable
import com.udacity.asteroidradar.room.AsteroidRoom
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Asteroid(val id: Long, val codename: String, val closeApproachDate: String,
                    val absoluteMagnitude: Double, val estimatedDiameter: Double,
                    val relativeVelocity: Double, val distanceFromEarth: Double,
                    val isPotentiallyHazardous: Boolean) : Parcelable


@SuppressLint("NewApi")
fun List<Asteroid>.asDatabaseData(): List<AsteroidRoom> {
    return map {
        val calendar : Calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())
        calendar.time = dateFormat.parse(it.closeApproachDate)
        AsteroidRoom(
            id = it.id,
            codeName = it.codename,
            closeApproachDate = calendar.timeInMillis,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}
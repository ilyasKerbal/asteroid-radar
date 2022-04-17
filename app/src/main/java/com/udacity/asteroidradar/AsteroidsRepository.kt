package com.udacity.asteroidradar

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.Api
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.room.AsteroidRoomDatabase
import com.udacity.asteroidradar.room.asDomainData
import kotlinx.coroutines.*
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AsteroidsRepository(private val asteroidRoomDatabase: AsteroidRoomDatabase) {

    val asteroids : LiveData<List<Asteroid>> = Transformations.map(asteroidRoomDatabase.asteroidRoomDao.getAsteroids()) {
        it.asDomainData()
    }

    @SuppressLint("NewApi")
    suspend fun loadAsteroids() {
        /**
         * Written based on
         * https://developer.android.com/reference/java/util/Calendar
         * https://stackoverflow.com/questions/33199084/how-to-get-next-seven-days-in-android
         * */

        val handler = CoroutineExceptionHandler{ _, exception ->
            exception.message?.let {
                Log.e("AsteroidsRepository", "Cannot load data due to external error: ${exception.message}")
            }
        }
        try {
            val dateFormatter = DateTimeFormatter.ofPattern(Constants.API_QUERY_DATE_FORMAT)
            val today = LocalDate.now().format(dateFormatter)
            val next7Days = LocalDate.now().plusDays(7L).format(dateFormatter)

            CoroutineScope(Dispatchers.IO).launch(handler) {
                val response = Api.retrofitService.getAsteroids(Constants.API_KEY, today, next7Days)
                val parsedResponse = parseAsteroidsJsonResult(JSONObject(response)).toList()
                asteroidRoomDatabase.asteroidRoomDao.insertAsteroids(parsedResponse.asDatabaseData())
            }

        } catch (e: Exception) {
            Log.i("AsteroidsRepository", "Failed to load Asteroids list")
        }
    }
}
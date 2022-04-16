package com.udacity.asteroidradar.main

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.Api
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MainViewModel : ViewModel() {

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay : LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _asteroids = MutableLiveData<List<Asteroid>>()
    val asteroids : LiveData<List<Asteroid>>
        get() = _asteroids

    init {
        loadPictureOfDay()
        loadAsteroids()
    }

    private fun loadPictureOfDay() {
        viewModelScope.launch {
            try {
                val picture = Api.retrofitService.getMediaOfDay(Constants.API_KEY)
                _pictureOfDay.value = picture
            } catch (e: Exception) {
                Log.i("MainViewModel", "Failed to load picture of the Day")
            }
        }
    }

    @SuppressLint("NewApi")
    private fun loadAsteroids() {
        /**
         * Written based on
         * https://developer.android.com/reference/java/util/Calendar
         * https://stackoverflow.com/questions/33199084/how-to-get-next-seven-days-in-android
         * */

        val handler = CoroutineExceptionHandler{ _, exception ->
            exception.message?.let {
                Log.e("MainViewModel", "Cannot load data due to external error")
            }
        }
        try {
            val dateFormatter = DateTimeFormatter.ofPattern(Constants.API_QUERY_DATE_FORMAT)
            val today = LocalDate.now().format(dateFormatter)
            val next7Days = LocalDate.now().plusDays(7L).format(dateFormatter)
            viewModelScope.launch(handler) {
                val response = Api.retrofitService.getAsteroids(Constants.API_KEY, today, next7Days)
                _asteroids.value = parseAsteroidsJsonResult(JSONObject(response)).toList()
            }
        } catch (e: Exception) {
            Log.i("MainViewModel", "Failed to load Asteroids list")
        }
    }
}
package com.udacity.asteroidradar.main

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.AsteroidsRepository
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.Api
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.room.AsteroidRoomDatabase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val database = AsteroidRoomDatabase.getInstance(application)
    private val repository = AsteroidsRepository(database)

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay : LiveData<PictureOfDay>
        get() = _pictureOfDay

    init {
        loadPictureOfDay()
        loadAsteroidsRepository()
    }

    private fun loadAsteroidsRepository() {
        viewModelScope.launch {
            repository.loadAsteroids()
        }
    }

    val asteroids = repository.asteroids

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
}

class MainViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)){
            return MainViewModel(app) as T
        }
        throw IllegalArgumentException("Cannot recognize the model class")
    }

}
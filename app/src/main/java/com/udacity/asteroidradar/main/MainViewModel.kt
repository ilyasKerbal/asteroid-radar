package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.Api
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay : LiveData<PictureOfDay>
        get() = _pictureOfDay

    init {
        loadPictureOfDay()
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
}
package com.udacity.asteroidradar.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidRoomDao {
    @Query("SELECT * FROM asteroid_table ORDER BY close_approach_date DESC")
    fun getAsteroids(): LiveData<List<AsteroidRoom>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsteroids(asteroids: List<AsteroidRoom>)
}
package com.udacity.asteroidradar.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [AsteroidRoom::class], version = 1, exportSchema = false)
abstract class AsteroidRoomDatabase : RoomDatabase() {

    abstract val asteroidRoomDao : AsteroidRoomDao

    companion object {
        @Volatile
        private var INSTANCE : AsteroidRoomDatabase? = null

        fun getInstance(context: Context) : AsteroidRoomDatabase {
            var instance = INSTANCE
            synchronized(AsteroidRoomDatabase::class.java) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, AsteroidRoomDatabase::class.java, "asteroid_database")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                INSTANCE = instance
            }
            return instance!!
        }
    }
}
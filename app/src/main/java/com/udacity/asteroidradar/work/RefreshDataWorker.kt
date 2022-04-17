package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.AsteroidsRepository
import com.udacity.asteroidradar.room.AsteroidRoomDatabase
import java.lang.Exception

class RefreshDataWorker(appContext: Context, workerParameters: WorkerParameters) : CoroutineWorker(appContext, workerParameters) {

    companion object {
        val DATA_WORKER_NAME = "asteroid_data_worker"
    }

    override suspend fun doWork(): Result {
        val database = AsteroidRoomDatabase.getInstance(applicationContext)
        val repository = AsteroidsRepository(database)
        return try {
            repository.loadAsteroids()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
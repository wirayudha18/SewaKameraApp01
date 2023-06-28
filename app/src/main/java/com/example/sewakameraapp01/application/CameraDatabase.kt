package com.example.sewakameraapp01.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sewakameraapp01.dao.CameraDao
import com.example.sewakameraapp01.model.Camera

@Database(entities = [Camera::class], version = 1, exportSchema = false)
abstract class CameraDatabase: RoomDatabase() {
    abstract fun cameraDao(): CameraDao

    companion object {
        private var INSTANCE: CameraDatabase? = null

        fun getDatabase(context: Context): CameraDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CameraDatabase::class.java,
                    "camera_database"
                )
                    .allowMainThreadQueries()
                    .build()

                    INSTANCE = instance
                    instance
            }
        }
    }
}
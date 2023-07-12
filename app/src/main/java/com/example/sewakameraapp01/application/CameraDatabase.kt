package com.example.sewakameraapp01.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.sewakameraapp01.dao.CameraDao
import com.example.sewakameraapp01.model.Camera

@Database(entities = [Camera::class], version = 2, exportSchema = false)
abstract class CameraDatabase: RoomDatabase() {
    abstract fun cameraDao(): CameraDao

    companion object {
        private var INSTANCE: CameraDatabase? = null

        //migrasi database versi 1 ke 2, karena ada perubbahan table tadi
        private val migration1To2: Migration = object: Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE camera_table ADD COLUMN latitude Double DEFAULT 0.0")
                database.execSQL("ALTER TABLE camera_table ADD COLUMN longitude Double DEFAULT 0.0")
            }
        }

        fun getDatabase(context: Context): CameraDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CameraDatabase::class.java,
                    "camera_database"
                )
                    .addMigrations(migration1To2)
                    .allowMainThreadQueries()
                    .build()

                    INSTANCE = instance
                    instance
            }
        }
    }
}
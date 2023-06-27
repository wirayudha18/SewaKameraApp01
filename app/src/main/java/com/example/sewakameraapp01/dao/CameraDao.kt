package com.example.sewakameraapp01.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.sewakameraapp01.model.Camera
import kotlinx.coroutines.flow.Flow

@Dao
interface CameraDao {
    @Query("SELECT * FROM camera_table ORDER BY name ASC")
    fun getAllCamera(): Flow<List<Camera>>

    @Insert
    suspend fun insertCamera(camera: Camera)

    @Delete
    suspend fun deleteCamera(camera: Camera)

    @Update fun updateCamera(camera: Camera)

}
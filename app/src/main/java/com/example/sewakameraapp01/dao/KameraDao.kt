package com.example.sewakameraapp01.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.sewakameraapp01.model.Kamera
import kotlinx.coroutines.flow.Flow

@Dao
interface KameraDao {
    @Query("SELECT * FROM kamera_table ORDER BY name ASC")
    fun getAllKamera(): Flow<List<Kamera>>

    @Insert
    suspend fun insertKamera(kamera: Kamera)

    @Delete
    suspend fun deleteKamera(kamera: Kamera)

    @Update fun updateKamera(kamera: Kamera)

}
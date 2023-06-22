package com.example.sewakameraapp01.repository

import com.example.sewakameraapp01.dao.KameraDao
import com.example.sewakameraapp01.model.Kamera
import kotlinx.coroutines.flow.Flow

class KameraRepository(private val kameraDao: KameraDao) {
    val allKameras: Flow<List<Kamera>> =kameraDao.getAllKamera()

    suspend fun insertKamera(kamera: Kamera) {
        kameraDao.insertKamera(kamera)
    }

    suspend fun deleteKamera(kamera: Kamera) {
        kameraDao.deleteKamera(kamera)
    }

    suspend fun updateKamera(kamera: Kamera) {
        kameraDao.updateKamera(kamera)
    }
}
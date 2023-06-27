package com.example.sewakameraapp01.repository

import com.example.sewakameraapp01.dao.CameraDao
import com.example.sewakameraapp01.model.Camera
import kotlinx.coroutines.flow.Flow

class KameraRepository(private val cameraDao: CameraDao) {
    val allCameras: Flow<List<Camera>> =cameraDao.getAllCamera()

    suspend fun insertCamera(camera: Camera) {
        cameraDao.insertCamera(camera)
    }

    suspend fun deleteCamera(camera: Camera) {
        cameraDao.deleteCamera(camera)
    }

    suspend fun updateCamera(camera: Camera) {
        cameraDao.updateCamera(camera)
    }
}
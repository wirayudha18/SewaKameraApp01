package com.example.sewakameraapp01.application

import android.app.Application
import com.example.sewakameraapp01.repository.CameraRepository


class CameraApp: Application() {
    val database by lazy { CameraDatabase.getDatabase(this) }
    val repository by lazy { CameraRepository(database.cameraDao()) }
}
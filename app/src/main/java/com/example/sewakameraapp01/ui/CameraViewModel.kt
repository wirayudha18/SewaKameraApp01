package com.example.sewakameraapp01.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.sewakameraapp01.model.Camera
import com.example.sewakameraapp01.repository.CameraRepository
import kotlinx.coroutines.launch

class CameraViewModel(private val repository: CameraRepository): ViewModel() {
    val allCameras: LiveData<List<Camera>> = repository.allCameras.asLiveData()

    fun insert(camera: Camera) = viewModelScope.launch {
        repository.insertCamera(camera)
    }

    fun delete(camera: Camera) = viewModelScope.launch {
        repository.deleteCamera(camera)
    }

    fun update(camera: Camera) = viewModelScope.launch {
        repository.updateCamera(camera)
    }
}

class CameraViewModelFactory(private val repository: CameraRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((CameraViewModel::class.java))) {
            return CameraViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}
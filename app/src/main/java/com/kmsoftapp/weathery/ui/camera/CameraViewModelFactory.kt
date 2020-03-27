package com.kmsoftapp.weathery.ui.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kmsoftapp.weathery.data.ThumbnailRepository

class CameraViewModelFactory(
    private val thumbnailRepository: ThumbnailRepository

) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CameraPreviewViewModel(thumbnailRepository) as T
    }
}
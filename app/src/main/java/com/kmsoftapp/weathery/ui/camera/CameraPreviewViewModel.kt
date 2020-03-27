package com.kmsoftapp.weathery.ui.camera

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.kmsoftapp.weathery.data.ThumbnailRepository
import com.kmsoftapp.weathery.data.db.entity.ImageEntry
import com.kmsoftapp.weathery.internal.lazyDeferred
import com.otaliastudios.cameraview.PictureResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CameraPreviewViewModel(
    //    private val fetchedImage: ImageEntry? = fetchedImage
    private val thumbnailRepository: ThumbnailRepository
) : ViewModel() {

    val thumbnails by
    lazyDeferred {
        thumbnailRepository.getCashedThumbnails()
    }

    fun insertThumbnail(result: ImageEntry) = GlobalScope.launch {
        thumbnailRepository.insertThumbnail(result)
    }

    fun saveToInternalStorage(bitmap: Bitmap, context: Context) = GlobalScope.run {
        return@run thumbnailRepository.saveBitmapToInternalStorage(bitmap, context)
    }

    fun convertByteArrayToBitmap(pictureResult: PictureResult) = GlobalScope.run {
        return@run thumbnailRepository.convertByteArrayToBitmap(pictureResult)
    }

    fun overlayTextOnImage(
        bitmap: Bitmap,
        temperatureText: String,
        feelsLikeText: String,
        windText: String,
        location: String
    ) = GlobalScope.run {
        return@run thumbnailRepository.overlayTextOnImage(
            bitmap,
            temperatureText,
            feelsLikeText,
            windText, location
        )
    }
}

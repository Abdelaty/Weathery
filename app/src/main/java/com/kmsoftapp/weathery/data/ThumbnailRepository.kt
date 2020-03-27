package com.kmsoftapp.weathery.data

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.kmsoftapp.weathery.data.db.entity.ImageEntry
import com.otaliastudios.cameraview.PictureResult

interface ThumbnailRepository {
    suspend fun getCashedThumbnails(): LiveData<List<ImageEntry>>
    suspend fun insertThumbnail(fetchedImage: ImageEntry)
    fun saveBitmapToInternalStorage(bitmap: Bitmap, context: Context): ImageEntry
    fun convertByteArrayToBitmap(pictureResult: PictureResult): Bitmap
    fun overlayTextOnImage(
        bitmap: Bitmap,
        temperatureText: String,
        feelsLikeText: String,
        windText: String,
        location: String
    ): Bitmap


}
package com.kmsoftapp.weathery.data

import android.content.Context
import android.content.ContextWrapper
import android.graphics.*
import android.net.Uri
import androidx.lifecycle.LiveData
import com.kmsoftapp.weathery.data.db.entity.ImageEntry
import com.kmsoftapp.weathery.data.db.entity.SavedImagesDao
import com.kmsoftapp.weathery.internal.Constants.Companion.OVERLAYED_TEXT_SIZE
import com.otaliastudios.cameraview.PictureResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ThumbnailRepositoryImpl(private val savedImagesDao: SavedImagesDao) : ThumbnailRepository {

    override suspend fun getCashedThumbnails(): LiveData<List<ImageEntry>> {
        return withContext(Dispatchers.IO) {
            return@withContext savedImagesDao.getImages()
        }

    }

    override suspend fun insertThumbnail(fetchedImage: ImageEntry) {
        GlobalScope.launch(Dispatchers.IO) {
            savedImagesDao.upsert(fetchedImage)
        }
    }

    override fun saveBitmapToInternalStorage(bitmap: Bitmap, context: Context): ImageEntry {
        val cw = ContextWrapper(context)
        val directory: File = cw.getDir("imageDir", Context.MODE_PRIVATE)
        val mypath = File(directory, System.currentTimeMillis().toString() + ".jpg")
        val uri = Uri.fromFile(mypath)
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            val value: Any = try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return ImageEntry(thumbnailPath = directory.absolutePath, uri = uri.toString())
    }

    override fun convertByteArrayToBitmap(pictureResult: PictureResult): Bitmap {
        return BitmapFactory.decodeByteArray(pictureResult.data, 0, pictureResult.data.size)
    }

    override fun overlayTextOnImage(
        bitmap: Bitmap,
        temperatureText: String,
        feelsLikeText: String,
        windText: String,
        location: String
    ): Bitmap {
        return overlayText(
            bitmap,
            temperatureText,
            feelsLikeText,
            windText,
            location,
            OVERLAYED_TEXT_SIZE
        )
    }

    private fun overlayText(
        src: Bitmap,
        temperatureText: String,
        feelsLikeText: String,
        windText: String,
        location: String,
        overlayedTextSize: Int

    ): Bitmap {
        val mutableBitmap: Bitmap = src.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)
        val paint = Paint()
        paint.color = Color.WHITE
        paint.textSize = overlayedTextSize.toFloat()
        paint.isAntiAlias = true
        canvas.drawBitmap(mutableBitmap, 0F, 0F, paint)
        canvas.drawText(temperatureText, 0F, 50F, paint)
        canvas.drawText(feelsLikeText, 0F, 70F, paint)
        canvas.drawText(windText, 0F, 90F, paint)
        canvas.drawText(location, 0F, 110F, paint)

        return mutableBitmap
    }
}
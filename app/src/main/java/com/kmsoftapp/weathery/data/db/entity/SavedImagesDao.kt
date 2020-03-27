package com.kmsoftapp.weathery.data.db.entity

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SavedImagesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(imageEntry: ImageEntry)

    @Query("select * from saved_images ")
    fun getImages(): LiveData<List<ImageEntry>>


}
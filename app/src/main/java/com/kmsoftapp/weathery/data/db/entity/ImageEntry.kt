package com.kmsoftapp.weathery.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_images")
data class ImageEntry(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "path")
    val thumbnailPath: String,

    @ColumnInfo(name = "uri")
    val uri: String
) {
    companion object {
        const val TABLE_NAME = "saved_images"
        const val ID = "id"
        const val BYTES = "image"
    }
}


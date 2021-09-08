package com.quangnguyen.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.quangnguyen.data.database.DatabaseConfig
import com.quangnguyen.data.database.ImageDataConverter

@Entity(tableName = DatabaseConfig.IMAGE_TABLE_NAME)
@TypeConverters(ImageDataConverter::class)
data class ImageModel(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "urls") val urls: ImageUrlsList,
    @ColumnInfo(name = "user") val user: User,
    @Expose(serialize = false, deserialize = false)
    @ColumnInfo(name = "downloaded_file_path") var downloadedFilePath: String?)

data class ImageUrlsList(val raw: String, val full: String, val regular: String, val small: String,
    val thumb: String)

data class User(val id: String, val username: String, val name: String)
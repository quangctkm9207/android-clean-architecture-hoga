package com.quangnguyen.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.quangnguyen.data.model.ImageModel
import io.reactivex.Single

@Dao
interface ImageDao {

  @Query("SELECT * FROM ${DatabaseConfig.IMAGE_TABLE_NAME}")
  fun getImages(): Single<List<ImageModel>>

  @Query("SELECT * FROM ${DatabaseConfig.IMAGE_TABLE_NAME} WHERE id = :imageId")
  fun getImage(imageId: String): ImageModel

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun insertImage(imageModel: ImageModel)

  @Update
  fun updateImage(imageModel: ImageModel)
}
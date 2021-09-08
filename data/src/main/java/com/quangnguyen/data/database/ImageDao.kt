package com.quangnguyen.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.quangnguyen.data.model.ImageModel
import io.reactivex.rxjava3.core.Single

@Dao
interface ImageDao {

  @Query("SELECT * FROM ${DatabaseConfig.IMAGE_TABLE_NAME}")
  fun getImages(): Single<List<ImageModel>>

  @Query("SELECT * FROM ${DatabaseConfig.IMAGE_TABLE_NAME} WHERE id = :imageId")
  fun getImage(imageId: String): Single<ImageModel>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun insertImage(imageModel: ImageModel)

  @Update
  fun updateImage(imageModel: ImageModel)
}
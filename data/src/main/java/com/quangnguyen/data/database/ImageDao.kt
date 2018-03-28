package com.quangnguyen.data.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.quangnguyen.data.model.ImageModel
import io.reactivex.Single

@Dao
interface ImageDao {

  @Query("SELECT * FROM ${DatabaseConfig.IMAGE_TABLE_NAME}")
  fun getImages(): Single<List<ImageModel>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertImage(imageModel: ImageModel)
}
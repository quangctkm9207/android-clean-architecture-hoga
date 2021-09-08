package com.quangnguyen.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quangnguyen.data.model.ImageModel

@Database(entities = [(ImageModel::class)], version = 1)
abstract class HogaDb : RoomDatabase() {

  abstract fun imageDao(): ImageDao
}
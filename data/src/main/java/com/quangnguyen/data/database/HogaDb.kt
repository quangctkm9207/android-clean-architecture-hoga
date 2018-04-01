package com.quangnguyen.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.quangnguyen.data.model.ImageModel

@Database(entities = [(ImageModel::class)], version = 1)
abstract class HogaDb : RoomDatabase() {

  abstract fun imageDao(): ImageDao
}
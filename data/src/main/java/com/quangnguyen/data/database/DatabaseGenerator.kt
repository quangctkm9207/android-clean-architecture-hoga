package com.quangnguyen.data.database

import android.content.Context
import androidx.room.Room

/**
 * Creates database related stuff's implementations
 */
object DatabaseGenerator {

  private fun getCartridgeManagerDb(context: Context): HogaDb {
    return Room.databaseBuilder(context.applicationContext, HogaDb::class.java,
        DatabaseConfig.DATABASE_NAME)
        .build()
  }

  fun getImageDao(context: Context): ImageDao {
    return getCartridgeManagerDb(context).imageDao()
  }
}
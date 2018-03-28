package com.quangnguyen.data.database

import android.arch.persistence.room.Room
import android.content.Context


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
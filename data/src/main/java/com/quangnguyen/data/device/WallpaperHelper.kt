package com.quangnguyen.data.device

import android.app.WallpaperManager
import android.content.Context
import io.reactivex.Completable
import java.net.URL

class WallpaperHelper(context: Context) {

  private val wallpaperManager = WallpaperManager.getInstance(context)

  fun setWallpaper(imageUrl: String): Completable {
    return Completable.create {
      val inputStream = URL(imageUrl).openStream()
      wallpaperManager.setStream(inputStream)

      it.onComplete()
    }
  }
}
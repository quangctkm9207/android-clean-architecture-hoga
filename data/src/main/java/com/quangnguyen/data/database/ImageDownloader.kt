package com.quangnguyen.data.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import io.reactivex.Completable
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL

/**
 * Downloads image from a url via internet
 */
class ImageDownloader {

  val separator = File.separator
  val imageFolderName = "Hoga${separator}Images"
  val imageFolderPath = "${Environment.getExternalStorageDirectory()}$separator$imageFolderName"

  fun download(downloadUrl: String, imageFileName: String): Completable {
    return Completable.create({
      val bitmap: Bitmap?
      try {
        val input = URL(downloadUrl).openStream()
        bitmap = BitmapFactory.decodeStream(input)
        saveBitmapToFile(bitmap, imageFileName)
        it.onComplete()
      } catch (error: IOException) {
        it.onError(error)
      }
    })
  }

  private fun saveBitmapToFile(bitmap: Bitmap, imageFileName: String) {
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

    val file = File(getImageFilePath(imageFileName))
    file.createNewFile()
    val fileOutput = FileOutputStream(file)
    fileOutput.write(bytes.toByteArray())

    fileOutput.close()
  }

  private fun getImageFilePath(imageFileName: String): String {
    return "$imageFolderPath$separator$imageFileName"
  }
}
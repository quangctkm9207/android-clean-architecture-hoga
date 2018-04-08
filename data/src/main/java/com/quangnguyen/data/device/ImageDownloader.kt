package com.quangnguyen.data.device

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

  private val separator = File.separator
  private val imageFolderName = "Hoga"
  private val externalDirPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
  private val imageFolderPath = "${externalDirPath.path}$separator$imageFolderName"

  fun getImageFilePath(imageFileName: String): String {
    val imageExtension = ".jpg"
    return "$imageFolderPath$separator$imageFileName$imageExtension"
  }

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
    createFolderIfNotExist()

    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)

    val filePath = getImageFilePath(imageFileName)
    val file = File(filePath)
    file.createNewFile()
    val fileOutput = FileOutputStream(file)
    fileOutput.write(bytes.toByteArray())

    fileOutput.close()
  }

  private fun createFolderIfNotExist() {
    val imageFolder = File(imageFolderPath)
    if (!imageFolder.exists()) {
      imageFolder.mkdir()
    }
  }
}
package com.quangnguyen.hoga.util

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

fun AppCompatActivity.isStoragePermissionGranted(): Boolean {
  return if (Build.VERSION.SDK_INT >= 23) {
    if (checkSelfPermission(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
      true
    } else {
      ActivityCompat.requestPermissions(this,
          arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
          1)
      false
    }
  } else {
    //permission is automatically granted on sdk<23 upon installation
    true
  }
}
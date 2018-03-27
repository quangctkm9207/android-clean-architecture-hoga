package com.quangnguyen.hoga.util

import android.Manifest
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.isStoragePermissionGranted(): Boolean {
  if (Build.VERSION.SDK_INT >= 23) {
    if (checkSelfPermission(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
      return true
    } else {
      ActivityCompat.requestPermissions(this,
          arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
          1)
      return false
    }
  } else {
    //permission is automatically granted on sdk<23 upon installation
    return true
  }
}
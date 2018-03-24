package com.quangnguyen.hoga.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.quangnguyen.hoga.R
import com.quangnguyen.hoga.R.layout
import com.quangnguyen.hoga.ui.images.ImagesFragment

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)

    supportFragmentManager.beginTransaction().add(R.id.container, ImagesFragment()).commit()
  }
}

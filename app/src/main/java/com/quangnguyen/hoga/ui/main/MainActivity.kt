package com.quangnguyen.hoga.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.quangnguyen.hoga.R
import com.quangnguyen.hoga.R.layout
import com.quangnguyen.hoga.ui.collection.CollectionFragment
import com.quangnguyen.hoga.ui.images.ImagesFragment
import kotlinx.android.synthetic.main.activity_main.navigationView

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)

    setupBottomNavigation()
    title = getString(R.string.explore)
    replaceFragment(ImagesFragment())
  }

  private fun setupBottomNavigation() {
    navigationView.enableAnimation(false)
    navigationView.enableShiftingMode(false)
    navigationView.enableItemShiftingMode(false)
    navigationView.setOnNavigationItemSelectedListener { item ->
      // Set screen title
      this@MainActivity.title = item.title
      // Update corresponding fragment
      when (item.itemId) {
        R.id.nav_explore -> replaceFragment(ImagesFragment())
        R.id.nav_collection -> replaceFragment(CollectionFragment())
      }
      true
    }
  }

  private fun replaceFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
  }
}

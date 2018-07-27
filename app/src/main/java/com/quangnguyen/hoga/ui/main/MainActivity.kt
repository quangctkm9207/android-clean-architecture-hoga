package com.quangnguyen.hoga.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.quangnguyen.hoga.R
import com.quangnguyen.hoga.R.layout
import com.quangnguyen.hoga.ui.collection.CollectionFragment
import com.quangnguyen.hoga.ui.explore.ExploreFragment
import kotlinx.android.synthetic.main.activity_main.navigationView

class MainActivity : AppCompatActivity() {

  companion object {
    private const val EXPLORE_FRAGMENT_TAG = "explore_fragment"
    private const val COLLECTION_FRAGMENT_TAG = "collection_fragment"
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)

    setSupportActionBar(findViewById(R.id.toolbar))

    setupBottomNavigation()
    title = getString(R.string.explore)

    replaceFragment(ExploreFragment(), EXPLORE_FRAGMENT_TAG)
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
        R.id.nav_explore -> replaceFragment(ExploreFragment(), EXPLORE_FRAGMENT_TAG)
        R.id.nav_collection -> replaceFragment(CollectionFragment(), COLLECTION_FRAGMENT_TAG)
      }
      true
    }
  }

  private fun replaceFragment(fragment: Fragment, tag: String) {
    val fragmentByTag = supportFragmentManager.findFragmentByTag(tag)
    if (fragmentByTag == null) {
      supportFragmentManager.beginTransaction().replace(R.id.container, fragment,
          tag).commit()
    } else {
      supportFragmentManager.beginTransaction().replace(R.id.container, fragmentByTag,
          tag).commit()
    }
  }
}

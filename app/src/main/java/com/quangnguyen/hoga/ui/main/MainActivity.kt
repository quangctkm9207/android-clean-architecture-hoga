package com.quangnguyen.hoga.ui.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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

    setupBottomNavigation()
    title = getString(R.string.explore)

    replaceExploreFragment()
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
        R.id.nav_explore -> replaceExploreFragment()
        R.id.nav_collection -> replaceCollectionFragment()
      }
      true
    }
  }

  private fun replaceExploreFragment() {
    val exploreFragment = supportFragmentManager.findFragmentByTag(EXPLORE_FRAGMENT_TAG)
    if (exploreFragment == null) {
      supportFragmentManager.beginTransaction().replace(R.id.container, ExploreFragment(),
          EXPLORE_FRAGMENT_TAG).commit()
    } else {
      supportFragmentManager.beginTransaction().replace(R.id.container, exploreFragment,
          EXPLORE_FRAGMENT_TAG).commit()
    }
  }

  private fun replaceCollectionFragment() {
    val collectionFragment = supportFragmentManager.findFragmentByTag(COLLECTION_FRAGMENT_TAG)
    if (collectionFragment == null) {
      supportFragmentManager.beginTransaction().replace(R.id.container, CollectionFragment(),
          COLLECTION_FRAGMENT_TAG).commit()
    } else {
      supportFragmentManager.beginTransaction().replace(R.id.container, collectionFragment,
          COLLECTION_FRAGMENT_TAG).commit()
    }
  }
}

package com.quangnguyen.hoga.ui.collection

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.quangnguyen.hoga.R


class CollectionFragment: Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?): View? {
     return inflater.inflate(R.layout.fragment_collection, container, false)
  }
}
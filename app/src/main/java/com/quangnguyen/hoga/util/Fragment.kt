package com.quangnguyen.hoga.util

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

/**
 *  Hides virtual keyboard on screen using Fragment.
 */
fun Fragment.hideKeyboard(context: Context) {
  val windowToken = view?.rootView?.windowToken
  windowToken?.let {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
  }
}
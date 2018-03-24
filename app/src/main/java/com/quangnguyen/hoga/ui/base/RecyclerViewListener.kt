package com.quangnguyen.hoga.ui.base

import android.view.View


interface RecyclerViewListener {

  @FunctionalInterface
  interface OnItemClickListener {
    fun OnItemClick(view: View, position: Int)
  }

  @FunctionalInterface
  interface OnItemLongClickListener {
    fun OnItemLongClick(view: View, position: Int)
  }
}
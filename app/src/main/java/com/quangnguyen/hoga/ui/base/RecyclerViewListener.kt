package com.quangnguyen.hoga.ui.base

import android.view.View


interface RecyclerViewListener {

  @FunctionalInterface
  interface OnItemClickListener {
    fun onItemClick(view: View, position: Int)
  }

  @FunctionalInterface
  interface OnItemLongClickListener {
    fun onItemLongClick(view: View, position: Int)
  }
}
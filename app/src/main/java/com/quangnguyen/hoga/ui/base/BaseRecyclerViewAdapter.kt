package com.quangnguyen.hoga.ui.base

import android.support.v7.widget.RecyclerView


/**
 * A general RecyclerViewAdapter which supports OnItemClickListener and OnItemLongClickListener.
 */
abstract class BaseRecyclerViewAdapter<VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  private var itemClickListener: RecyclerViewListener.OnItemClickListener? = null
  private var itemLongClickListener: RecyclerViewListener.OnItemLongClickListener? = null

  fun setOnItemClickListener(
      itemClickListener: RecyclerViewListener.OnItemClickListener) {
    this.itemClickListener = itemClickListener
  }

  fun setOnItemLongClickListener(
      itemLongClickListener: RecyclerViewListener.OnItemLongClickListener) {
    this.itemLongClickListener = itemLongClickListener
  }

  /**
   * Override onBindViewHolder to support OnItemClick and OnItemLongClick listener.
   */
  override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, i: Int) {
    if (itemClickListener != null) {
      viewHolder.itemView.setOnClickListener { view -> itemClickListener!!.OnItemClick(view, i) }
    }
    if (itemLongClickListener != null) {
      viewHolder.itemView.setOnLongClickListener { view ->
        itemLongClickListener!!.OnItemLongClick(view, i)
        true
      }
    }
  }
}
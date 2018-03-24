package com.quangnguyen.hoga.ui.images

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.quangnguyen.hoga.R
import com.quangnguyen.hoga.domain.model.Image
import com.quangnguyen.hoga.ui.base.BaseRecyclerViewAdapter

class ImageAdapter(var images: List<Image>) : BaseRecyclerViewAdapter<ImageAdapter.ImageViewHolder>() {

  lateinit var context: Context

  override fun onCreateViewHolder(parent: ViewGroup,
      viewType: Int): android.support.v7.widget.RecyclerView.ViewHolder {
    this.context = parent.context
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
    return ImageViewHolder(view)
  }

  override fun onBindViewHolder(viewHolder: android.support.v7.widget.RecyclerView.ViewHolder,
      i: Int) {
    val image = images[i]
    val vh = viewHolder as ImageViewHolder
    Glide.with(vh.image).load(image.smallImageUrl).into(vh.image)
    vh.authorText.text = image.authorName
  }

  override fun getItemCount(): Int {
    return images.size
  }

  fun replaceData(images: List<Image>) {
    this.images = images
    notifyDataSetChanged()
  }

  fun getData(): List<Image> {
    return images
  }

  fun clearData() {
    this.images = emptyList()
    notifyDataSetChanged()
  }

  class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var image: ImageView
    var authorText: TextView

    init {
      image = view.findViewById(R.id.image)
      authorText = view.findViewById(R.id.text_author)
    }
  }
}
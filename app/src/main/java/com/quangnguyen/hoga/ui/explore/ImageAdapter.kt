package com.quangnguyen.hoga.ui.explore

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.quangnguyen.hoga.R
import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.ui.base.BaseRecyclerViewAdapter
import java.io.File

class ImageAdapter(
    private var images: List<Image>) : BaseRecyclerViewAdapter<ImageAdapter.ImageViewHolder>() {

  private lateinit var context: Context

  override fun onCreateViewHolder(parent: ViewGroup,
      viewType: Int): android.support.v7.widget.RecyclerView.ViewHolder {
    this.context = parent.context
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
    return ImageViewHolder(view)
  }

  override fun onBindViewHolder(viewHolder: android.support.v7.widget.RecyclerView.ViewHolder,
      i: Int) {
    super.onBindViewHolder(viewHolder, i)
    val image = images[i]
    val vh = viewHolder as ImageViewHolder
    // Show image in storage if it has been downloaded already
    if (image.downloadedFilePath != null) {
      val imageUri = Uri.fromFile(File(image.downloadedFilePath))
      Glide.with(vh.image).load(imageUri).into(vh.image)
    } else {
      // If not, show it from web
      Glide.with(vh.image).load(image.smallImageUrl).into(vh.image)
    }
    vh.authorText.text = image.authorName
  }

  override fun getItemCount(): Int {
    return images.size
  }

  fun replaceData(images: List<Image>) {
    this.images = images
    notifyDataSetChanged()
  }

  fun getItem(position: Int): Image {
    return images[position]
  }

  fun getData(): List<Image> {
    return images
  }

  fun clearData() {
    this.images = emptyList()
    notifyDataSetChanged()
  }

  class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var image: ImageView = view.findViewById(R.id.image)
    var authorText: TextView

    init {
      authorText = view.findViewById(R.id.text_author)
    }
  }
}
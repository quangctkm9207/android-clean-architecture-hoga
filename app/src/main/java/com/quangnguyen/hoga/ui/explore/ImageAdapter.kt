package com.quangnguyen.hoga.ui.explore

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.quangnguyen.hoga.R
import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.ui.base.BaseRecyclerViewAdapter
import java.io.File

class ImageAdapter(
    private var images: MutableList<Image>) : BaseRecyclerViewAdapter<ImageAdapter.ImageViewHolder>() {

  private lateinit var context: Context

  override fun onCreateViewHolder(parent: ViewGroup,
      viewType: Int): RecyclerView.ViewHolder {
    this.context = parent.context
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
    return ImageViewHolder(view)
  }

  override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder,
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
    this.images.clear()
    this.images.addAll(images)
    notifyDataSetChanged()
  }

  fun addData(newImages: List<Image>) {
    this.images.addAll(newImages)
    notifyDataSetChanged()
  }

  fun getItem(position: Int): Image {
    return images[position]
  }

  fun getData(): List<Image> {
    return images
  }

  fun clearData() {
    this.images.clear()
    notifyDataSetChanged()
  }

  class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var image: ImageView = view.findViewById(R.id.image)
    var authorText: TextView = view.findViewById(R.id.text_author)
  }
}
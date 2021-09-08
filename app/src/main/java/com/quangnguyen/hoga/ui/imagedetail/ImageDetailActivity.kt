package com.quangnguyen.hoga.ui.imagedetail

import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.quangnguyen.hoga.R
import com.quangnguyen.hoga.di.Injector
import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.ui.imagedetail.ImageDetailContract.Presenter
import com.quangnguyen.hoga.util.isStoragePermissionGranted
import kotlinx.android.synthetic.main.activity_image_detail.authorText
import kotlinx.android.synthetic.main.activity_image_detail.imageView
import kotlinx.android.synthetic.main.activity_image_detail.loadingText
import kotlinx.android.synthetic.main.activity_image_detail.progressBar
import java.io.File

class ImageDetailActivity : AppCompatActivity(), ImageDetailContract.View {

  companion object {
    const val EXTRA_IMAGE_ID = "image_id"
  }

  private lateinit var presenter: Presenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_image_detail)

    setSupportActionBar(findViewById(R.id.toolbar))
  }

  override fun onStart() {
    super.onStart()
    initPresenter()
  }

  private fun initPresenter() {
    presenter = ImageDetailPresenter(this, Injector.getImageUseCase, Injector.downloadImageUseCase,
        Injector.setWallpaperUseCase,
        Injector.schedulerProvider)
  }

  override fun onResume() {
    super.onResume()
    presenter.attach()
    loadImageDetail()
  }

  private fun loadImageDetail() {
    val imageId = intent.getStringExtra(EXTRA_IMAGE_ID)
    if (imageId != null) {
      presenter.loadImage(imageId)
    }
  }

  override fun onPause() {
    super.onPause()
    presenter.detach()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.activity_image_detail, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.download -> {
        if (isStoragePermissionGranted()) {
          presenter.downloadImage()
        }
      }
      R.id.set_wallpaper -> presenter.setWallpaper()
    }
    return true
  }

  override fun showImage(image: Image) {
    var loadedResourcePath: Any?
    loadedResourcePath = if (image.downloadedFilePath != null) {
      Uri.fromFile(File(image.downloadedFilePath))
    } else {
      // If not, show it from web
      image.downloadUrl
    }
    progressBar.visibility = View.VISIBLE
    Glide.with(this)
        .load(loadedResourcePath)
        .listener(object : RequestListener<Drawable> {
          override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
              dataSource: DataSource?, isFirstResource: Boolean): Boolean {
            progressBar.visibility = View.GONE
            return false
          }

          override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?,
              isFirstResource: Boolean): Boolean {
            progressBar.visibility = View.GONE
            return false
          }
        })
        .into(this.imageView)
    authorText.text = String.format(getString(R.string.photo_by), image.authorName)
  }

  override fun showMessage(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
  }

  override fun showDownloadingIndicator() {
    showIndicator(getString(R.string.downloading))
  }

  override fun hideDownloadingIndicator() {
    hideIndicator()
  }

  override fun showSettingWallpaperIndicator() {
    showIndicator(getString(R.string.setting_wallpaper))
  }

  override fun hideSettingWallpaperIndicator() {
    hideIndicator()
  }

  private fun showIndicator(text: String) {
    loadingText.text = text
    loadingText.visibility = View.VISIBLE
    progressBar.visibility = View.VISIBLE
  }

  private fun hideIndicator() {
    loadingText.visibility = View.GONE
    progressBar.visibility = View.GONE
  }
}
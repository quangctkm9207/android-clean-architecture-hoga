package com.quangnguyen.hoga.ui.imagedetail

import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.ui.base.BasePresenter

interface ImageDetailContract {

  interface View {

    fun showImage(image: Image)

    fun showMessage(message: String)

    fun showDownloadingIndicator()

    fun hideDownloadingIndicator()
  }

  interface Presenter: BasePresenter {

    fun loadImage(id: String)

    fun downloadImage()

    fun setWallpaper()
  }
}
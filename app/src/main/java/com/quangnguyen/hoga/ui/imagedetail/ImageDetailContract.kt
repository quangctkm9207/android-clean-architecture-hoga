package com.quangnguyen.hoga.ui.imagedetail

import com.quangnguyen.hoga.domain.model.Image
import com.quangnguyen.hoga.ui.base.BasePresenter

interface ImageDetailContract {

  interface View {

    fun showImage(image: Image)

    fun showMessage(message: String)

    fun showDowloadingIndicator()

    fun hideDownloadingIndicator()
  }

  interface Presenter: BasePresenter {

    fun loadImage(id: String)

    fun downloadImage()
  }
}
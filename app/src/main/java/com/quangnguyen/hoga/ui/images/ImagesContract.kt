package com.quangnguyen.hoga.ui.images

import com.quangnguyen.hoga.domain.model.Image
import com.quangnguyen.hoga.ui.base.BasePresenter


interface ImagesContract {

  interface View {

    fun showTrendingImages(images: List<Image>)

    fun showErrorMessage(errorMsg: String)
  }

  interface Presenter : BasePresenter {

    fun loadTrendingImages()
  }
}
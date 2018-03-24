package com.quangnguyen.hoga.ui.images

import com.quangnguyen.hoga.domain.model.Image
import com.quangnguyen.hoga.ui.base.BasePresenter


interface ImagesContract {

  interface View {

    fun showImages(images: List<Image>)

    fun clearImages()

    fun showErrorMessage(errorMsg: String)

    fun startLoadingIndicator()

    fun stopLoadingIndicator()
  }

  interface Presenter : BasePresenter {

    fun loadTrendingImages()

    fun searchImages(keyword: String)
  }
}
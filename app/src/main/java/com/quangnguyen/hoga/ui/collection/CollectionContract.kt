package com.quangnguyen.hoga.ui.collection

import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.ui.base.BasePresenter


interface CollectionContract {

  interface View {

    fun showDownloadedImages(images: List<Image>)

    fun showErrorMessage(errorMsg: String)

    fun showImageDetail(imageId: String)
  }

  interface Presenter : BasePresenter {

    fun loadDownloadedImages()

    fun loadImageDetail(imageId: String)
  }
}
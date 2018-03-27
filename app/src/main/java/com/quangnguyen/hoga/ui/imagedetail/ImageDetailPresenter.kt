package com.quangnguyen.hoga.ui.imagedetail

import com.quangnguyen.hoga.domain.interactor.image.DownloadImageUsecase
import com.quangnguyen.hoga.domain.interactor.image.GetImageUseCase
import com.quangnguyen.hoga.domain.model.Image
import com.quangnguyen.hoga.ui.imagedetail.ImageDetailContract.Presenter
import com.quangnguyen.hoga.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable


class ImageDetailPresenter(private val view: ImageDetailContract.View,
    private val getImageUseCase: GetImageUseCase,
    private val downloadImageUsecase: DownloadImageUsecase,
    private val schedulerProvider: SchedulerProvider) : Presenter {

  private val compositeDisposable = CompositeDisposable()

  var image: Image? = null

  override fun attach() {

  }

  override fun detach() {
    compositeDisposable.clear()
  }

  override fun loadImage(id: String) {
    val disposable = getImageUseCase.execute(id)
        .subscribeOn(schedulerProvider.ioScheduler)
        .observeOn(schedulerProvider.uiScheduler)
        .subscribe({
          this.image = it
          view.showImage(it)
        }, {
          view.showMessage(it.localizedMessage)
        })

    compositeDisposable.add(disposable)
  }

  override fun downloadImage() {
    if (image == null) {
      return
    }
    val disposable = downloadImageUsecase.execute(image!!)
        .subscribeOn(schedulerProvider.ioScheduler)
        .observeOn(schedulerProvider.uiScheduler)
        .subscribe({
          view.showMessage("Download complete!")
        }, {
          view.showMessage(it.localizedMessage)
        })

    compositeDisposable.add(disposable)
  }
}
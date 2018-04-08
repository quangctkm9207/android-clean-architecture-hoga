package com.quangnguyen.hoga.ui.imagedetail

import com.quangnguyen.hoga.domain.usecase.image.DownloadImageUseCase
import com.quangnguyen.hoga.domain.usecase.image.GetImageUseCase
import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.domain.usecase.image.SetWallpaperUseCase
import com.quangnguyen.hoga.ui.imagedetail.ImageDetailContract.Presenter
import com.quangnguyen.hoga.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class ImageDetailPresenter(private val view: ImageDetailContract.View,
    private val getImageUseCase: GetImageUseCase,
    private val downloadImageUseCase: DownloadImageUseCase,
    private val setWallpaperUseCase: SetWallpaperUseCase,
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

    view.showDownloadingIndicator()

    val disposable = downloadImageUseCase.execute(image!!)
        .subscribeOn(schedulerProvider.ioScheduler)
        .observeOn(schedulerProvider.uiScheduler)
        .subscribe({
          view.hideDownloadingIndicator()
          view.showMessage("Download complete!")
        }, {
          view.hideDownloadingIndicator()
          view.showMessage(it.localizedMessage)
        })

    compositeDisposable.add(disposable)
  }

  override fun setWallpaper() {
    if (image == null) {
      return
    }

    val disposable = setWallpaperUseCase.execute(image!!)
        .subscribeOn(schedulerProvider.ioScheduler)
        .observeOn(schedulerProvider.uiScheduler)
        .subscribe({
          view.showMessage("Complete setting wallpaper!")
        }, {
          view.showMessage(it.localizedMessage)
        })

    compositeDisposable.add(disposable)
  }
}
package com.quangnguyen.hoga.ui.collection

import com.quangnguyen.hoga.domain.interactor.image.LoadDownloadedImagesUseCase
import com.quangnguyen.hoga.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable


class CollectionPresenter(private val view: CollectionContract.View,
    private val loadDownloadedImagesUseCase: LoadDownloadedImagesUseCase,
    private val schedulerProvider: SchedulerProvider): CollectionContract.Presenter {

  private val compositeDisposable = CompositeDisposable()

  override fun attach() {
    loadDownloadedImages()
  }

  override fun detach() {
    compositeDisposable.clear()
  }

  override fun loadDownloadedImages() {
    val disposable = loadDownloadedImagesUseCase.execute()
        .subscribeOn(schedulerProvider.ioScheduler)
        .observeOn(schedulerProvider.uiScheduler)
        .subscribe({ images ->
          view.showDownloadedImages(images)
        }, { error ->
          view.showErrorMessage(error.localizedMessage)
        })

    compositeDisposable.add(disposable)
  }

  override fun loadImageDetail(imageId: String) {
    view.showImageDetail(imageId)
  }
}
package com.quangnguyen.hoga.ui.images

import com.quangnguyen.hoga.domain.interactor.image.LoadTrendingImageUseCase
import com.quangnguyen.hoga.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


class ImagesPresenter(val view: ImagesContract.View,
    val loadTrendingImageUseCase: LoadTrendingImageUseCase,
    val schedulerProvider: SchedulerProvider) : ImagesContract.Presenter {

  val compositeDisposable = CompositeDisposable()

  override fun attach() {
    loadTrendingImages()
  }

  override fun detach() {

  }

  override fun loadTrendingImages() {
    val disposable = loadTrendingImageUseCase.execute()
        .subscribeOn(schedulerProvider.ioScheduler)
        .observeOn(schedulerProvider.uiScheduler)
        .subscribe({ images ->
          view.showTrendingImages(images)
        }, { error ->
          view.showErrorMessage(error.localizedMessage)
        })

    compositeDisposable.add(disposable)
  }
}
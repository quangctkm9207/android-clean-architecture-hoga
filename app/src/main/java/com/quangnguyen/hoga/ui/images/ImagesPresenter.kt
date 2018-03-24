package com.quangnguyen.hoga.ui.images

import com.quangnguyen.hoga.domain.interactor.image.LoadTrendingImageUseCase
import com.quangnguyen.hoga.domain.interactor.image.SearchImageUseCase
import com.quangnguyen.hoga.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class ImagesPresenter(val view: ImagesContract.View,
    val loadTrendingImageUseCase: LoadTrendingImageUseCase,
    val searchImageUseCase: SearchImageUseCase,
    val schedulerProvider: SchedulerProvider) : ImagesContract.Presenter {

  val compositeDisposable = CompositeDisposable()

  override fun attach() {
    loadTrendingImages()
  }

  override fun detach() {
    compositeDisposable.clear()
  }

  override fun loadTrendingImages() {
    view.clearImages()
    view.startLoadingIndicator()

    val disposable = loadTrendingImageUseCase.execute()
        .subscribeOn(schedulerProvider.ioScheduler)
        .observeOn(schedulerProvider.uiScheduler)
        .subscribe({ images ->
          view.stopLoadingIndicator()
          view.showImages(images)
        }, { error ->
          view.stopLoadingIndicator()
          view.showErrorMessage(error.localizedMessage)
        })

    compositeDisposable.add(disposable)
  }

  override fun searchImages(keyword: String) {
    view.clearImages()
    view.startLoadingIndicator()

    val disposable = searchImageUseCase.execute(keyword)
        .subscribeOn(schedulerProvider.ioScheduler)
        .observeOn(schedulerProvider.uiScheduler)
        .subscribe({ images ->
          view.stopLoadingIndicator()
          view.showImages(images)
        }, { error ->
          view.stopLoadingIndicator()
          view.showErrorMessage(error.localizedMessage)
        })

    compositeDisposable.add(disposable)
  }
}
package com.quangnguyen.hoga.ui.images

import com.quangnguyen.hoga.domain.interactor.image.LoadTrendingImagesUseCase
import com.quangnguyen.hoga.domain.interactor.image.SearchImagesUseCase
import com.quangnguyen.hoga.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class ImagesPresenter(val view: ImagesContract.View,
    val loadTrendingImagesUseCase: LoadTrendingImagesUseCase,
    val searchImagesUseCase: SearchImagesUseCase,
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

    val disposable = loadTrendingImagesUseCase.execute()
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

    val disposable = searchImagesUseCase.execute(keyword)
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
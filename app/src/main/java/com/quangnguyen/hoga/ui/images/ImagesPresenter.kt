package com.quangnguyen.hoga.ui.images

import com.quangnguyen.hoga.domain.usecase.image.LoadTrendingImagesUseCase
import com.quangnguyen.hoga.domain.usecase.image.SearchImagesUseCase
import com.quangnguyen.hoga.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class ImagesPresenter(private val view: ImagesContract.View,
    private val loadTrendingImagesUseCase: LoadTrendingImagesUseCase,
    private val searchImagesUseCase: SearchImagesUseCase,
    private val schedulerProvider: SchedulerProvider) : ImagesContract.Presenter {

  private val compositeDisposable = CompositeDisposable()

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

  override fun loadImageDetail(imageId: String) {
    view.showImageDetail(imageId)
  }
}
package com.quangnguyen.hoga.ui.explore

import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.domain.usecase.image.LoadMoreTrendingImagesUseCase
import com.quangnguyen.hoga.domain.usecase.image.LoadTrendingImagesUseCase
import com.quangnguyen.hoga.domain.usecase.image.SearchImagesUseCase
import com.quangnguyen.hoga.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class ExplorePresenter(private val view: ExploreContract.View,
    private val loadTrendingImagesUseCase: LoadTrendingImagesUseCase,
    private val loadMoreTrendingImagesUseCase: LoadMoreTrendingImagesUseCase,
    private val searchImagesUseCase: SearchImagesUseCase,
    private val schedulerProvider: SchedulerProvider) : ExploreContract.Presenter {

  private val compositeDisposable = CompositeDisposable()

  private var caches: MutableList<Image> = ArrayList()

  override fun attach() {
    if (!caches.isEmpty()) {
      view.showImages(caches)
    } else {
      loadTrendingImages()
    }
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

          replaceCaches(images)
        }, { error ->
          view.stopLoadingIndicator()
          view.showErrorMessage(error.localizedMessage)
        })

    compositeDisposable.add(disposable)
  }

  override fun loadMoreTrendingImages() {
    view.startLoadingIndicator()

    val disposable = loadMoreTrendingImagesUseCase.execute()
        .subscribeOn(schedulerProvider.ioScheduler)
        .observeOn(schedulerProvider.uiScheduler)
        .subscribe({ newImages ->
          view.stopLoadingIndicator()
          view.showMoreImages(newImages)

          caches.addAll(newImages)
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

          replaceCaches(images)
        }, { error ->
          view.stopLoadingIndicator()
          view.showErrorMessage(error.localizedMessage)
        })

    compositeDisposable.add(disposable)
  }

  override fun loadImageDetail(imageId: String) {
    view.showImageDetail(imageId)
  }

  private fun replaceCaches(images: List<Image>) {
    caches.clear()
    caches.addAll(images)
  }
}
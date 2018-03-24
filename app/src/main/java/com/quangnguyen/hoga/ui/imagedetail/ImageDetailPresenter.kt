package com.quangnguyen.hoga.ui.imagedetail

import com.quangnguyen.hoga.domain.interactor.image.GetImageUseCase
import com.quangnguyen.hoga.ui.imagedetail.ImageDetailContract.Presenter
import com.quangnguyen.hoga.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable


class ImageDetailPresenter(private val view: ImageDetailContract.View, private val getImageUseCase: GetImageUseCase, private val schedulerProvider: SchedulerProvider) : Presenter {

  private val compositeDisposable = CompositeDisposable()

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
          image -> view.showImage(image)
        }, {
          error -> view.showErrorMessage(error.localizedMessage)
        })

    compositeDisposable.add(disposable)
  }
}
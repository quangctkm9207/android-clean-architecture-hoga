package com.quangnguyen.hoga.domain.interactor.image

import com.quangnguyen.hoga.domain.interactor.CompletableUseCaseWithParam
import com.quangnguyen.hoga.domain.model.Image
import com.quangnguyen.hoga.domain.repository.ImageRepository
import io.reactivex.Completable

class DownloadImageUsecase(private val imageRepository: ImageRepository): CompletableUseCaseWithParam<Image> {

  override fun execute(param: Image): Completable {
    return imageRepository.downloadImage(image = param)
  }
}
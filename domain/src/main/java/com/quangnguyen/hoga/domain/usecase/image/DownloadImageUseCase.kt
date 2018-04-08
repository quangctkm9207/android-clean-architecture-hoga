package com.quangnguyen.hoga.domain.usecase.image

import com.quangnguyen.hoga.domain.usecase.CompletableUseCaseWithParam
import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.domain.repository.ImageRepository
import io.reactivex.Completable

class DownloadImageUseCase(private val imageRepository: ImageRepository): CompletableUseCaseWithParam<Image> {

  override fun execute(param: Image): Completable {
    return imageRepository.downloadImage(image = param)
  }
}
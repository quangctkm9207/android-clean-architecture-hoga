package com.quangnguyen.hoga.domain.interactor.image

import com.quangnguyen.hoga.domain.interactor.SingleUseCaseWithParam
import com.quangnguyen.hoga.domain.model.Image
import com.quangnguyen.hoga.domain.repository.ImageRepository
import io.reactivex.Single


class GetImageUseCase(private val imageRepository: ImageRepository) : SingleUseCaseWithParam<String, Image> {

  override fun execute(imageId: String): Single<Image> {
    return imageRepository.getImage(imageId)
  }
}
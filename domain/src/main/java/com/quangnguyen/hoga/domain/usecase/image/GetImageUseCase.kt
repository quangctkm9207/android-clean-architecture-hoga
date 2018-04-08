package com.quangnguyen.hoga.domain.usecase.image

import com.quangnguyen.hoga.domain.usecase.SingleUseCaseWithParam
import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.domain.repository.ImageRepository
import io.reactivex.Single

class GetImageUseCase(private val imageRepository: ImageRepository) : SingleUseCaseWithParam<String, Image> {

  override fun execute(param: String): Single<Image> {
    return imageRepository.getImage(imageId = param)
  }
}
package com.quangnguyen.hoga.domain.usecase.image

import com.quangnguyen.hoga.domain.usecase.SingleUseCaseWithParam
import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.domain.repository.ImageRepository
import io.reactivex.rxjava3.core.Single

class SearchImagesUseCase(
    private val imageRepository: ImageRepository) : SingleUseCaseWithParam<String, List<Image>> {

  override fun execute(param: String): Single<List<Image>> {
    return imageRepository.searchImages(keyword = param)
  }
}
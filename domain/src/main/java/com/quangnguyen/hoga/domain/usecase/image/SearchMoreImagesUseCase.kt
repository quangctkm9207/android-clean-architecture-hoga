package com.quangnguyen.hoga.domain.usecase.image

import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.domain.repository.ImageRepository
import com.quangnguyen.hoga.domain.usecase.SingleUseCaseWithParam
import io.reactivex.rxjava3.core.Single

class SearchMoreImagesUseCase(
    private val imageRepository: ImageRepository) : SingleUseCaseWithParam<String, List<Image>> {

  override fun execute(param: String): Single<List<Image>> {
    return imageRepository.searchMoreImages(keyword = param)
  }
}
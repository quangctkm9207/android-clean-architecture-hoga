package com.quangnguyen.hoga.domain.interactor.image

import com.quangnguyen.hoga.domain.interactor.SingleUseCaseWithParam
import com.quangnguyen.hoga.domain.model.Image
import com.quangnguyen.hoga.domain.repository.ImageRepository
import io.reactivex.Single

class SearchImagesUseCase(
    private val imageRepository: ImageRepository) : SingleUseCaseWithParam<String, List<Image>> {

  override fun execute(param: String): Single<List<Image>> {
    return imageRepository.searchImages(keyword = param)
  }
}
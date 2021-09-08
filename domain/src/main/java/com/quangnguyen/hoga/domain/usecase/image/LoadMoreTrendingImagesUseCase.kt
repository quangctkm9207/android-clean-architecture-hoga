package com.quangnguyen.hoga.domain.usecase.image

import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.domain.repository.ImageRepository
import com.quangnguyen.hoga.domain.usecase.SingleUseCase
import io.reactivex.rxjava3.core.Single

class LoadMoreTrendingImagesUseCase(
    private val imageRepository: ImageRepository) : SingleUseCase<List<Image>> {

  override fun execute(): Single<List<Image>> {
    return imageRepository.loadMoreTrendingImages()
  }
}
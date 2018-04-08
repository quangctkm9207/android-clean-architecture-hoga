package com.quangnguyen.hoga.domain.usecase.image

import com.quangnguyen.hoga.domain.usecase.SingleUseCase
import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.domain.repository.ImageRepository
import io.reactivex.Single

class LoadTrendingImagesUseCase(private val imageRepository: ImageRepository) : SingleUseCase<List<Image>> {

  override fun execute(): Single<List<Image>> {
    return imageRepository.loadTrendingImages()
  }
}
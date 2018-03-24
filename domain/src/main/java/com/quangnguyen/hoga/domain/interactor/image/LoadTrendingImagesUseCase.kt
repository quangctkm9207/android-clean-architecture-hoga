package com.quangnguyen.hoga.domain.interactor.image

import com.quangnguyen.hoga.domain.interactor.SingleUseCase
import com.quangnguyen.hoga.domain.model.Image
import com.quangnguyen.hoga.domain.repository.ImageRepository
import io.reactivex.Single

class LoadTrendingImagesUseCase(private val imageRepository: ImageRepository) : SingleUseCase<List<Image>> {

  override fun execute(): Single<List<Image>> {
    return imageRepository.loadTrendingImages()
  }
}
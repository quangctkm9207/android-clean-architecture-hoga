package com.quangnguyen.hoga.domain.usecase.image

import com.quangnguyen.hoga.domain.entity.Image
import com.quangnguyen.hoga.domain.repository.ImageRepository
import com.quangnguyen.hoga.domain.usecase.CompletableUseCaseWithParam
import io.reactivex.Completable


class SetWallpaperUseCase(
    private val repository: ImageRepository) : CompletableUseCaseWithParam<Image> {

  override fun execute(param: Image): Completable {
    return repository.setWallpaper(param)
  }
}
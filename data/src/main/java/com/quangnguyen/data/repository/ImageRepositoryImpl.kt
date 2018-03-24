package com.quangnguyen.data.repository

import com.quangnguyen.data.BuildConfig
import com.quangnguyen.data.api.ImageService
import com.quangnguyen.data.mapper.ImageMapper
import com.quangnguyen.hoga.domain.model.Image
import com.quangnguyen.hoga.domain.repository.ImageRepository
import io.reactivex.Flowable
import io.reactivex.Single

class ImageRepositoryImpl(val imageService: ImageService,
    val imageMapper: ImageMapper) : ImageRepository {
  val token = BuildConfig.UNSPLASH_TOKEN

  override fun loadTrendingImage(): Single<List<Image>> {
    return imageService.loadTrendingImages(token)
        .toFlowable()
        .flatMap { Flowable.fromIterable(it) }
        .map { imageMapper.dataToDomain(it) }
        .toList()
  }

  override fun searchImage(keyword: String): Single<List<Image>> {
    return imageService.searchImages(token, keyword)
        .map { it.results }
        .toFlowable()
        .flatMap { Flowable.fromIterable(it) }
        .map { imageMapper.dataToDomain(it) }
        .toList()
  }
}
package com.quangnguyen.hoga.di

import com.quangnguyen.data.api.ServiceGenerator
import com.quangnguyen.data.database.ImageDownloader
import com.quangnguyen.data.mapper.ImageMapperImpl
import com.quangnguyen.data.repository.ImageRepositoryImpl
import com.quangnguyen.hoga.domain.interactor.image.DownloadImageUsecase
import com.quangnguyen.hoga.domain.interactor.image.GetImageUseCase
import com.quangnguyen.hoga.domain.interactor.image.LoadTrendingImagesUseCase
import com.quangnguyen.hoga.domain.interactor.image.SearchImagesUseCase
import com.quangnguyen.hoga.util.SchedulerProvider

class Injector {

  companion object {
    lateinit var loadTrendingImagesUseCase: LoadTrendingImagesUseCase
    lateinit var searchImagesUseCase: SearchImagesUseCase
    lateinit var getImageUseCase: GetImageUseCase
    lateinit var downloadImageUseCase: DownloadImageUsecase

    lateinit var schedulerProvider: SchedulerProvider

    fun initialize() {
      val imageService = ServiceGenerator.provideImageService()
      val imageMapper = ImageMapperImpl()
      val imageDownloader = ImageDownloader()
      val imageRepository = ImageRepositoryImpl(imageService, imageDownloader, imageMapper)
      loadTrendingImagesUseCase = LoadTrendingImagesUseCase(imageRepository)
      searchImagesUseCase = SearchImagesUseCase(imageRepository)
      getImageUseCase = GetImageUseCase(imageRepository)
      downloadImageUseCase = DownloadImageUsecase(imageRepository)

      schedulerProvider = SchedulerProvider()
    }
  }
}
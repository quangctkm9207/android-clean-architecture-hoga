package com.quangnguyen.hoga.di

import android.app.Application
import com.quangnguyen.data.api.ServiceGenerator
import com.quangnguyen.data.database.DatabaseGenerator
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

    fun initialize(app: Application) {
      val imageService = ServiceGenerator.provideImageService()
      val imageDao = DatabaseGenerator.getImageDao(app)
      val imageMapper = ImageMapperImpl()
      val imageDownloader = ImageDownloader()
      val imageRepository = ImageRepositoryImpl(imageService, imageDao, imageDownloader,
          imageMapper)
      loadTrendingImagesUseCase = LoadTrendingImagesUseCase(imageRepository)
      searchImagesUseCase = SearchImagesUseCase(imageRepository)
      getImageUseCase = GetImageUseCase(imageRepository)
      downloadImageUseCase = DownloadImageUsecase(imageRepository)

      schedulerProvider = SchedulerProvider()
    }
  }
}
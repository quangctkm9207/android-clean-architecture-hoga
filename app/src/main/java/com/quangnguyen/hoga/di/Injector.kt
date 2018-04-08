package com.quangnguyen.hoga.di

import android.app.Application
import com.quangnguyen.data.api.ServiceGenerator
import com.quangnguyen.data.database.DatabaseGenerator
import com.quangnguyen.data.database.ImageDownloader
import com.quangnguyen.data.mapper.ImageMapperImpl
import com.quangnguyen.data.repository.ImageRepositoryImpl
import com.quangnguyen.hoga.domain.usecase.image.DownloadImageUseCase
import com.quangnguyen.hoga.domain.usecase.image.GetImageUseCase
import com.quangnguyen.hoga.domain.usecase.image.LoadDownloadedImagesUseCase
import com.quangnguyen.hoga.domain.usecase.image.LoadTrendingImagesUseCase
import com.quangnguyen.hoga.domain.usecase.image.SearchImagesUseCase
import com.quangnguyen.hoga.util.SchedulerProvider

class Injector {

  companion object {
    lateinit var loadTrendingImagesUseCase: LoadTrendingImagesUseCase
    lateinit var searchImagesUseCase: SearchImagesUseCase
    lateinit var getImageUseCase: GetImageUseCase
    lateinit var downloadImageUseCase: DownloadImageUseCase
    lateinit var loadDownloadedImagesUseCase: LoadDownloadedImagesUseCase

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
      downloadImageUseCase = DownloadImageUseCase(imageRepository)
      loadDownloadedImagesUseCase = LoadDownloadedImagesUseCase(imageRepository)

      schedulerProvider = SchedulerProvider()
    }
  }
}
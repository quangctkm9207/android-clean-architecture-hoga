package com.quangnguyen.data.repository

import com.quangnguyen.data.BuildConfig
import com.quangnguyen.data.api.ApiConfig
import com.quangnguyen.data.api.ImageService
import com.quangnguyen.data.database.ImageDao
import com.quangnguyen.data.database.ImageDownloader
import com.quangnguyen.data.mapper.ImageMapper
import com.quangnguyen.hoga.domain.model.Image
import com.quangnguyen.hoga.domain.repository.ImageRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

class ImageRepositoryImpl(private val imageService: ImageService,
    private val imageDao: ImageDao,
    private val imageDownloader: ImageDownloader,
    private val imageMapper: ImageMapper) : ImageRepository {
  private val token = BuildConfig.UNSPLASH_TOKEN

  override fun loadTrendingImages(): Single<List<Image>> {
    return imageService.loadTrendingImages(token, ApiConfig.DEFAULT_PAGE,
        ApiConfig.DEFAULT_PER_PAGE, ApiConfig.DEFAULT_ORDER_BY)
        .toFlowable()
        .flatMap { Flowable.fromIterable(it) }
        .doOnNext {
          imageDao.insertImage(it)
        }
        .map { imageMapper.dataToDomain(it) }
        .toList()
  }

  override fun searchImages(keyword: String): Single<List<Image>> {
    return imageService.searchImages(token, keyword, ApiConfig.DEFAULT_PAGE,
        ApiConfig.DEFAULT_PER_PAGE, ApiConfig.DEFAULT_ORDER_BY)
        .map { it.results }
        .toFlowable()
        .flatMap { Flowable.fromIterable(it) }
        .doOnNext {
          imageDao.insertImage(it)
        }
        .map { imageMapper.dataToDomain(it) }
        .toList()
  }

  override fun getImage(imageId: String): Single<Image> {
    return imageDao.getImage(imageId)
        .map { imageMapper.dataToDomain(it) }
  }

  override fun downloadImage(image: Image): Completable {
    return if (image.downloadedFilePath != null) {
      Completable.error(
          android.support.v4.os.OperationCanceledException("Image has been downloaded already."))
    } else {
      val imageFileName = "${image.id}_${image.authorName.replace(" ", "")}"
      imageDownloader.download(image.downloadUrl, imageFileName)
          .toSingle { imageDao.getImage(image.id) }
          .doOnSuccess {
            val imageModel = it.blockingGet()
            imageModel.downloadedFilePath = imageDownloader.getImageFilePath(imageFileName)
            imageDao.updateImage(imageModel)
          }
          .toCompletable()
    }
  }

  override fun loadDownloadedImages(): Single<List<Image>> {
    return imageDao.getImages()
        .toFlowable()
        .flatMap { Flowable.fromIterable(it) }
        .map { imageMapper.dataToDomain(it) }
        .filter { it.downloadedFilePath != null }
        .toList()
  }
}
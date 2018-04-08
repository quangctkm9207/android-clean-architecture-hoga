package com.quangnguyen.data.mapper

import com.quangnguyen.data.model.ImageModel
import com.quangnguyen.hoga.domain.entity.Image

class ImageMapperImpl: ImageMapper {

  override fun dataToDomain(imageModel: ImageModel): Image {
    return Image(imageModel.id, imageModel.urls.small, imageModel.urls.full, imageModel.user.name, imageModel.downloadedFilePath)
  }
}
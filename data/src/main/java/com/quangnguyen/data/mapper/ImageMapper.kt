package com.quangnguyen.data.mapper

import com.quangnguyen.data.model.ImageModel
import com.quangnguyen.hoga.domain.entity.Image

interface ImageMapper {

  fun dataToDomain(imageModel: ImageModel): Image
}
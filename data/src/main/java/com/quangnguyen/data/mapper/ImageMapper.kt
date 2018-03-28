package com.quangnguyen.data.mapper

import com.quangnguyen.data.model.ImageModel
import com.quangnguyen.hoga.domain.model.Image

interface ImageMapper {

  fun dataToDomain(imageModel: ImageModel): Image
}
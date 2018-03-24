package com.quangnguyen.data.model

import com.google.gson.annotations.SerializedName

data class SearchResultModel(val total: Int, @SerializedName("total_pages") val totalPages: Int,
    val results: List<ImageModel>)
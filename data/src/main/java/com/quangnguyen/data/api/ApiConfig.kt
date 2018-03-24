package com.quangnguyen.data.api


class ApiConfig {
  companion object {
    const val AUTH_HEADER = "Authorization"
    const val IMAGE_BASE_HOST = "https://api.unsplash.com/"
    const val QUERY = "query"
    const val PAGE = "page"
    const val PER_PAGE = "per_page"
    const val ORDER_BY = "order_by"

    const val DEFAULT_PAGE = 1
    const val DEFAULT_PER_PAGE = 20
    const val DEFAULT_ORDER_BY = "latest"
  }
}
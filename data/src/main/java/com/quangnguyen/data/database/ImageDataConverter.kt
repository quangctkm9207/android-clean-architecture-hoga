package com.quangnguyen.data.database

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.quangnguyen.data.model.ImageUrlsList
import com.quangnguyen.data.model.User

class ImageDataConverter {

  @TypeConverter
  fun textToUrls(text: String): ImageUrlsList {
    return Gson().fromJson(text, ImageUrlsList::class.java)
  }

  @TypeConverter
  fun urlsToText(imageUrlsList: ImageUrlsList): String {
    return Gson().toJson(imageUrlsList)
  }

  @TypeConverter
  fun textToUser(text: String): User {
    return Gson().fromJson(text, User::class.java)
  }

  @TypeConverter
  fun userToText(user: User): String {
    return Gson().toJson(user)
  }
}
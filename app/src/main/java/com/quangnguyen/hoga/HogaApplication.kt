package com.quangnguyen.hoga

import android.app.Application
import com.quangnguyen.hoga.di.Injector


class HogaApplication: Application() {

  override fun onCreate() {
    super.onCreate()
    Injector.initialize()
  }
}
package com.quangnguyen.hoga.util

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class SchedulerProvider {

  val uiScheduler: Scheduler = AndroidSchedulers.mainThread()
  val ioScheduler: Scheduler = Schedulers.io()
}

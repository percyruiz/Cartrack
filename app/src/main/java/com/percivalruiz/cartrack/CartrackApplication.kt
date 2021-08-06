package com.percivalruiz.cartrack

import android.app.Application
import com.percivalruiz.cartrack.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CartrackApplication : Application() {
  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidContext(this@CartrackApplication)
      modules(appModule)
    }
  }
}

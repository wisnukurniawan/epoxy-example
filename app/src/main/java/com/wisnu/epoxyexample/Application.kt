package com.wisnu.epoxyexample

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.wisnu.epoxyexample.di.modules

class Application : Application() {

  override fun onCreate() {
    super.onCreate()
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

    initDi()
  }

  private fun initDi() {
    startKoin {
      androidContext(this@Application)
      modules(modules)
    }
  }

}
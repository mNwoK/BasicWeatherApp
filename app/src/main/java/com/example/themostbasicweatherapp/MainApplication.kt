package com.example.themostbasicweatherapp

import android.app.Application
import com.example.themostbasicweatherapp.data.network.ApiClient
import com.example.themostbasicweatherapp.data.network.createHttpClient
import com.example.themostbasicweatherapp.viewmodels.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
  single { createHttpClient() }
  single { ApiClient(get())}
  viewModelOf(::MainViewModel)
}

class MainApplication : Application() {
  override fun onCreate() {
    super.onCreate()

    startKoin {
      androidContext(this@MainApplication)
      modules(appModule)
    }
  }
}

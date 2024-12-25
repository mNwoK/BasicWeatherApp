package com.example.themostbasicweatherapp

import android.app.Application
import androidx.room.Room
import com.example.themostbasicweatherapp.data.db.CityDataBase
import com.example.themostbasicweatherapp.data.network.ApiClient
import com.example.themostbasicweatherapp.data.network.createHttpClient
import com.example.themostbasicweatherapp.util.isOnline
import com.example.themostbasicweatherapp.viewmodels.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
  single { createHttpClient() }
  single { ApiClient(get())}
  single { Room.databaseBuilder(get(), CityDataBase::class.java, "CityDataBase").build().getDao()}
  single {isOnline(get()) }
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

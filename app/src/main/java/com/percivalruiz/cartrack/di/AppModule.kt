package com.percivalruiz.cartrack.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.percivalruiz.cartrack.api.ApiService
import com.percivalruiz.cartrack.data.Account
import com.percivalruiz.cartrack.data.LoginStatePrefs
import com.percivalruiz.cartrack.data.LoginStatePrefsImpl
import com.percivalruiz.cartrack.db.AppDatabase
import com.percivalruiz.cartrack.repository.*
import com.percivalruiz.cartrack.ui.login.LoginViewModel
import com.percivalruiz.cartrack.ui.splash.SplashViewModel
import com.percivalruiz.cartrack.ui.user.MainViewModel
import com.percivalruiz.cartrack.ui.user.detail.UserDetailViewModel
import com.percivalruiz.cartrack.ui.user.list.UserListViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.Executors

/**
 * Handles dependency injection using Koin
 */
val appModule = module {

  single {
    Moshi.Builder()
      .add(KotlinJsonAdapterFactory())
      .build()
  }

  single {
    val logger = HttpLoggingInterceptor { Log.d("API", it) }
    logger.level = HttpLoggingInterceptor.Level.BODY
    OkHttpClient.Builder()
      .addInterceptor(logger)
      .build()
  }

  single<ApiService> {
    val retrofit = Retrofit.Builder()
      .baseUrl("https://jsonplaceholder.typicode.com/")
      .client(get())
      .addConverterFactory(MoshiConverterFactory.create(get()))
      .build()
    retrofit.create(ApiService::class.java)
  }

  single {
    val db = Room.databaseBuilder(
      androidApplication(),
      AppDatabase::class.java, "cartrack"
    ).build()

    Executors.newSingleThreadScheduledExecutor().execute {
      db.accountDAO().insert(Account(id = 1, username = "username", password = "password"))
    }

    db
  }

  single {
    androidContext().getSharedPreferences("cartrack.login_status", Context.MODE_PRIVATE)
  }

  single<LoginStatePrefs> {
    LoginStatePrefsImpl(prefs = get())
  }

  // -- Repository --

  single<SessionRepository> {
    SessionRepositoryImpl(prefs = get())
  }

  single<LoginRepository> {
    LoginRepositoryImpl(db = get())
  }

  single<UserRepository> {
    UserRepositoryImpl(service = get(), db = get())
  }

  // -- ViewModel --

  viewModel {
    LoginViewModel(
      repository = get(),
      sessionRepository = get()
    )
  }

  viewModel {
    SplashViewModel(
      repository = get()
    )
  }

  viewModel {
    UserListViewModel(
      handle = get(),
      repository = get()
    )
  }

  viewModel {
    MainViewModel(
      repository = get()
    )
  }

  viewModel {
    UserDetailViewModel(
      handle = get(),
      repository = get()
    )
  }
}

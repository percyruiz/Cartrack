package com.percivalruiz.cartrack.di

import android.content.Context
import androidx.room.Room
import com.percivalruiz.cartrack.data.Account
import com.percivalruiz.cartrack.data.LoginStatePrefs
import com.percivalruiz.cartrack.data.LoginStatePrefsImpl
import com.percivalruiz.cartrack.db.AppDatabase
import com.percivalruiz.cartrack.repository.LoginRepository
import com.percivalruiz.cartrack.repository.LoginRepositoryImpl
import com.percivalruiz.cartrack.repository.LoginStateRepository
import com.percivalruiz.cartrack.repository.LoginStateRepositoryImpl
import com.percivalruiz.cartrack.ui.login.LoginViewModel
import com.percivalruiz.cartrack.ui.splash.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.concurrent.Executors

/**
 * Handles dependency injection using Koin
 */
val appModule = module {

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

  single<LoginRepository> {
    LoginRepositoryImpl(db = get())
  }

  single {
    androidContext().getSharedPreferences("cartrack.login_status", Context.MODE_PRIVATE)
  }

  single<LoginStatePrefs> {
    LoginStatePrefsImpl(prefs = get())
  }

  single<LoginStateRepository> {
    LoginStateRepositoryImpl(prefs = get())
  }

  viewModel {
    LoginViewModel(
      repository = get(),
      loginStateRepository = get()
    )
  }

  viewModel {
    SplashViewModel(
      repository = get()
    )
  }
}

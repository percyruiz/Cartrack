package com.percivalruiz.cartrack.di

import androidx.room.Room
import com.percivalruiz.cartrack.data.Account
import com.percivalruiz.cartrack.db.AppDatabase
import com.percivalruiz.cartrack.repository.LoginRepository
import com.percivalruiz.cartrack.repository.LoginRepositoryImpl
import com.percivalruiz.cartrack.ui.login.LoginViewModel
import org.koin.android.ext.koin.androidApplication
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

  viewModel {
    LoginViewModel(
      repository = get()
    )
  }
}

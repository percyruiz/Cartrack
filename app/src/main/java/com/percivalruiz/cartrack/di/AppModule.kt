package com.percivalruiz.cartrack.di

import com.percivalruiz.cartrack.repository.LoginRepository
import com.percivalruiz.cartrack.repository.LoginRepositoryImpl
import com.percivalruiz.cartrack.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Handles dependency injection using Koin
 */
val appModule = module {

  single<LoginRepository> {
    LoginRepositoryImpl()
  }

  viewModel {
    LoginViewModel(
      repository = get()
    )
  }
}

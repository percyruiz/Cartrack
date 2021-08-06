package com.percivalruiz.cartrack.ui.login

import androidx.lifecycle.ViewModel
import com.percivalruiz.cartrack.repository.LoginRepository

/**
 * Contains logic for user login
 *
 * @property handle implements caching of values binded on the viewmodel's lifecycle
 * @property repository data source for [ITunesItem] related data
 */
class LoginViewModel(
  private val repository: LoginRepository
): ViewModel() {

  fun login() {

  }
}

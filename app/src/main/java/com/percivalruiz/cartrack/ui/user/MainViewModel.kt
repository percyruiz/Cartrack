package com.percivalruiz.cartrack.ui.user

import androidx.lifecycle.ViewModel
import com.percivalruiz.cartrack.data.Account
import com.percivalruiz.cartrack.repository.LoginRepository
import com.percivalruiz.cartrack.repository.SessionRepository

/**
 * Contains logic for main screen
 *
 * @property repository data source for [Account] related data
 */
class MainViewModel(
  private val repository: SessionRepository
) : ViewModel() {
  fun logout() {
    repository.setState(false)
  }
}

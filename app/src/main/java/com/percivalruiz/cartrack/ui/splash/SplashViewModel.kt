package com.percivalruiz.cartrack.ui.splash

import androidx.lifecycle.ViewModel
import com.percivalruiz.cartrack.repository.SessionRepository

/**
 * Contains logic for login state
 *
 * @property repository data source for login state related data
 */
class SplashViewModel(
  private val repository: SessionRepository,
) : ViewModel() {

  fun isLoggedIn(): Boolean = repository.isLoggedIn()
}

package com.percivalruiz.cartrack.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.percivalruiz.cartrack.repository.LoginStateRepository
import kotlinx.coroutines.launch

/**
 * Contains logic for login state
 *
 * @property repository data source for login state related data
 */
class SplashViewModel(
  private val repository: LoginStateRepository,
) : ViewModel() {

  fun isLoggedIn(): Boolean = repository.isLoggedIn()
}

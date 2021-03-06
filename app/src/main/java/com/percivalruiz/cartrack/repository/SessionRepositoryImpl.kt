package com.percivalruiz.cartrack.repository

import com.percivalruiz.cartrack.data.LoginStatePrefs

/**
 * Data source class for login state related data
 *
 * @property prefs Object container for login state preferences
 */
class SessionRepositoryImpl(private val prefs: LoginStatePrefs) :
  SessionRepository {
  override fun isLoggedIn(): Boolean = prefs.isLoggedIn

  override fun setState(isLoggedIn: Boolean) {
    prefs.isLoggedIn = isLoggedIn
  }
}

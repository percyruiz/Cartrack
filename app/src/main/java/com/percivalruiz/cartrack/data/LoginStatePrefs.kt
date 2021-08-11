package com.percivalruiz.cartrack.data

import android.content.SharedPreferences

interface LoginStatePrefs {
  var isLoggedIn: Boolean
}

class LoginStatePrefsImpl(val prefs: SharedPreferences): LoginStatePrefs {
  override var isLoggedIn: Boolean
    set(value) {
      with (prefs.edit()) {
        putBoolean(IS_LOGGED_IN, value)
        apply()
      }
    }
    get() {
      return prefs.getBoolean(IS_LOGGED_IN, false)
    }

  companion object {
    private const val IS_LOGGED_IN = "isLoggedIn"
  }
}

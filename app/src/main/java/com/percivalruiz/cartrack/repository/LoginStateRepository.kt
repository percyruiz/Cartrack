package com.percivalruiz.cartrack.repository

interface LoginStateRepository {
  fun isLoggedIn(): Boolean
  fun setState(isLoggedIn: Boolean)
}

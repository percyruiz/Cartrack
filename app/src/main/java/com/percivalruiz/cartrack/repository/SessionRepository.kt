package com.percivalruiz.cartrack.repository

interface SessionRepository {
  fun isLoggedIn(): Boolean
  fun setState(isLoggedIn: Boolean)
}

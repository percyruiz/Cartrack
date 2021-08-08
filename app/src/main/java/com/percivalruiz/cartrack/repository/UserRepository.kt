package com.percivalruiz.cartrack.repository

import com.percivalruiz.cartrack.data.User

interface UserRepository {
  suspend fun getUsers(): List<User>
}

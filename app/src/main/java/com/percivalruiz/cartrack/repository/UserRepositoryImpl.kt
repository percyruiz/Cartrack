package com.percivalruiz.cartrack.repository

import com.percivalruiz.cartrack.api.ApiService
import com.percivalruiz.cartrack.data.User

/**
 * Data source class for user data
 *
 * @property service Interface for the API service
 */
class UserRepositoryImpl(val service: ApiService): UserRepository {

  override suspend fun getUsers(): List<User> = service.getUsers(0)
}

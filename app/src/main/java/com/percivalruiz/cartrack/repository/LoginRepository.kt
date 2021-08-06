package com.percivalruiz.cartrack.repository

import com.percivalruiz.cartrack.data.Account
import com.percivalruiz.cartrack.data.Result
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
  suspend fun login(username: String, password: String): Flow<Result<Account>>
}

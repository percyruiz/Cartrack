package com.percivalruiz.cartrack.repository

import com.percivalruiz.cartrack.data.Account
import com.percivalruiz.cartrack.data.Result
import com.percivalruiz.cartrack.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Data source class for [Account] related data
 *
 * @property db App's database instance
 */
class LoginRepositoryImpl(val db: AppDatabase) : LoginRepository {

  override suspend fun login(username: String, password: String): Flow<Result<Account>> {
    return flow {
      val result = safeDbCall { db.accountDAO().getAccount(username, password) }
      emit(result)

    }.flowOn(Dispatchers.IO)
  }

  private suspend fun <T> safeDbCall(dbCall: suspend () -> T): Result<T> {
    return try {
      val result = dbCall()
      if(result != null) {
        Result.success(result)
      } else {
        Result.error("Wrong username or password")
      }
    } catch (e: Exception) {
      Result.error("Something went wrong, $e")
    }
  }
}

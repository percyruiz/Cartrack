package com.percivalruiz.cartrack.repository

import androidx.paging.PagingData
import com.percivalruiz.cartrack.data.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
  suspend fun getUsers(): Flow<PagingData<User>>

  suspend fun getUser(id: Long): Flow<User?>
}

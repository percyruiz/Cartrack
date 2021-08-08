package com.percivalruiz.cartrack.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.percivalruiz.cartrack.api.ApiService
import com.percivalruiz.cartrack.data.User
import com.percivalruiz.cartrack.db.AppDatabase
import com.percivalruiz.cartrack.util.NETWORK_ITEM_SIZE
import kotlinx.coroutines.flow.Flow

/**
 * Data source class for [User]
 *
 * @property service Interface to API service
 * @property db App's database instance
 */
class UserRepositoryImpl(
  private val service: ApiService,
  private val db: AppDatabase
) : UserRepository {

  /**
   * Method for setting up a [Pager] object which will setup our service and db
   * The [Pager] handles querying of fresh data from the endpoint and using the db to
   * store a single source of truth to be used by our [RecyclerView]
   *
   * Returns [Flow<PagingData>] object the [Pager] produced by querying to db
   * [ApiService] requests data then inserts them to db using [UserRemoteMediator]
   */
  @OptIn(ExperimentalPagingApi::class)
  override suspend fun getUsers() = Pager(
    config = PagingConfig(NETWORK_ITEM_SIZE),
    remoteMediator = UserRemoteMediator(db, service)
  ) {
    db.userDAO().getAll()
  }.flow

  override suspend fun getUser(id: Long): Flow<User?> = db.userDAO().getById(id)
}

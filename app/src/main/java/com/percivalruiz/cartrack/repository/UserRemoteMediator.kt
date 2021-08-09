package com.percivalruiz.cartrack.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.percivalruiz.cartrack.api.ApiService
import com.percivalruiz.cartrack.data.RemoteKey
import com.percivalruiz.cartrack.data.User
import com.percivalruiz.cartrack.db.AppDatabase
import retrofit2.HttpException
import java.io.IOException

/**
 * RemoteMediator class used for Paging
 * Extends Android's [RemoteMediator] class
 * This handles loading of data from service and saving it on db
 * LoadType are passed to the load method which corresponds to the status of the adapter
 *
 * @property db App's database instance
 * @property service Interface to API service
 */
@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
  private val db: AppDatabase,
  private val service: ApiService,
) : RemoteMediator<Int, User>() {

  private val userDAO = db.userDAO()
  private val remotKeyDAO = db.remoteKeyDAO()

  override suspend fun load(
    loadType: LoadType,
    state: PagingState<Int, User>
  ): MediatorResult {
    try {
      val start = when (loadType) {
        LoadType.REFRESH -> null
        LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
        LoadType.APPEND -> {
          // Get the saved since value from db
          val remoteKey = db.withTransaction {
            remotKeyDAO.peek()
          }

          // Return the saved since value
          remoteKey?.nextPageKey ?: 0
        }
      }

      // Search endpoint service call
      val response = service.getUsers(
        start = start ?: 0,
      )

      db.withTransaction {
        // Remove [User] data saved in db cache when refresh is being called
        if (loadType == LoadType.REFRESH) {
          userDAO.nuke()
          remotKeyDAO.nuke()
        }

        // Update the next offset key value to db
        val lastPageKey = remotKeyDAO.peek()?.nextPageKey ?: 0
        remotKeyDAO.insert(
          RemoteKey(
            id = 0,
            nextPageKey = response.size + lastPageKey + 1
          )
        )

        // Cache to db
        userDAO.insert(*(response).toTypedArray())
      }

      // Set end of page if there is no more data being fetched and if search criteria is blank
      return MediatorResult.Success(endOfPaginationReached = response.isEmpty())
    } catch (e: IOException) {
      return MediatorResult.Error(e)
    } catch (e: HttpException) {
      return MediatorResult.Error(e)
    }
  }
}
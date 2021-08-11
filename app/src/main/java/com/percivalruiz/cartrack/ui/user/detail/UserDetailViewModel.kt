package com.percivalruiz.cartrack.ui.user.detail

import androidx.lifecycle.*
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.percivalruiz.cartrack.data.Result
import com.percivalruiz.cartrack.data.User
import com.percivalruiz.cartrack.repository.UserRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

/**
 * Contains logic for getting User detail
 *
 * @property repository data source for [User] related data
 */
class UserDetailViewModel(
  private val handle: SavedStateHandle,
  private val repository: UserRepository
) : ViewModel() {

  init {
    if (!handle.contains(USER_ID)) {
      handle.set(USER_ID, 0)
    }
  }

  /**
   * Observe changes from the handle as a [LiveData] object
   * This call API service's get users method on [LiveData] updates
   */
  @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
  val user = handle.getLiveData<Long>(USER_ID)
    .asFlow()
    .flatMapLatest { repository.getUser(it) }

  /**
   * Handles getting [User] by updating the handle
   *
   * @param id is the user's id on the db
   */
  fun getUser(id: Long) {
    handle.set(USER_ID, id)
  }

  companion object {
    const val USER_ID = "com.percivalruiz.cartrack.userId"
  }

}

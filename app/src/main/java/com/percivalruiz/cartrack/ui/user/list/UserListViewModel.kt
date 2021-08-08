package com.percivalruiz.cartrack.ui.user.list

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.percivalruiz.cartrack.data.User
import com.percivalruiz.cartrack.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flatMapLatest

/**
 * Contains logic for getting User list items
 *
 * @property repository data source for [User] related data
 */
class UserListViewModel(
  private val handle: SavedStateHandle,
  private val repository: UserRepository,
) : ViewModel() {

//  private val _users = Channel<PagingData<User>>(Channel.BUFFERED)
//  val users = _users.receiveAsFlow()

  init {
    handle.set(LOAD, true)
  }

  /**
   * Observe changes from the handle as a [LiveData] object
   * This call service's get users method on [LiveData] updates
   */
  @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
  val users = handle.getLiveData<Boolean>(LOAD)
    .asFlow()
    .flatMapLatest { repository.getUsers()}
    .cachedIn(viewModelScope)

  fun getUsers() {
    handle.set(LOAD, true)
  }

  companion object {
    const val LOAD = "com.percivalruiz.cartrack.load"
  }
}

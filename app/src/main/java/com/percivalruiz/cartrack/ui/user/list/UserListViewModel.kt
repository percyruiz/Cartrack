package com.percivalruiz.cartrack.ui.user.list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.percivalruiz.cartrack.api.ApiService
import com.percivalruiz.cartrack.data.User
import com.percivalruiz.cartrack.repository.LoginStateRepository
import com.percivalruiz.cartrack.repository.UserRepository
import kotlinx.coroutines.launch

/**
 * Contains logic for getting iTunes list items and saving user status
 *
 * @property repository data source for [User] related data
 */
class UserListViewModel(
  private val repository: UserRepository,
) : ViewModel() {

  fun getUsers() {
  }
}

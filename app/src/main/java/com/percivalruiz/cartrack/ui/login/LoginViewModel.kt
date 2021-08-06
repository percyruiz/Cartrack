package com.percivalruiz.cartrack.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.percivalruiz.cartrack.data.Account
import com.percivalruiz.cartrack.data.Result
import com.percivalruiz.cartrack.repository.LoginRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Contains logic for user login
 *
 * @property repository data source for [Account] related data
 */
class LoginViewModel(
  private val repository: LoginRepository,
  private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

  private val _loginUserFlow = Channel<Result<Account>>(Channel.BUFFERED)
  val loginUserFlow = _loginUserFlow.receiveAsFlow()

  @InternalCoroutinesApi
  fun login(username: String, password: String) {
    viewModelScope.launch(dispatcher) {
      repository.login(username, password)
        .catch { e ->
          _loginUserFlow.send(Result.error(e.toString()))
        }
        .collect {
          _loginUserFlow.send(it)
        }
    }
  }
}

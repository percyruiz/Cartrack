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

  private val _username = MutableStateFlow("")
  private val _password = MutableStateFlow("")

  val isLoginButtonEnabled: Flow<Boolean> = combine(_username, _password) { username, password ->
    val isUsernameValid = username.isNotBlank()
    val isPasswordValid = password.isNotBlank()
    return@combine isUsernameValid and isPasswordValid
  }

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

  fun setUsername(username: String) {
    _username.value = username
  }

  fun setPassword(password: String) {
    _password.value = password
  }

}

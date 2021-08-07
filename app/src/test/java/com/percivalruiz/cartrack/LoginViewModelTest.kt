package com.percivalruiz.cartrack

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.jraska.livedata.test
import com.percivalruiz.cartrack.data.Account
import com.percivalruiz.cartrack.data.Result
import com.percivalruiz.cartrack.repository.LoginRepository
import com.percivalruiz.cartrack.repository.LoginStateRepository
import com.percivalruiz.cartrack.ui.login.LoginViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  private val dispatcher = TestCoroutineDispatcher()

  private lateinit var underTest: LoginViewModel

  @MockK
  private lateinit var repository: LoginRepository

  @MockK
  private lateinit var loginStateRepo: LoginStateRepository

  private val account = Account(1,"username", "password")

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    Dispatchers.setMain(dispatcher)
    underTest = LoginViewModel(repository, loginStateRepo, dispatcher)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
    dispatcher.cleanupTestCoroutines()
  }

  @InternalCoroutinesApi
  @Test
  fun `Should be able to login successfully`() = runBlockingTest {
    val result = Result.success(account)
    coEvery { repository.login("username", "password") } returns flow {
      emit(result)
    }
    underTest.login("username", "password")

    underTest.loginUserFlow.asLiveData().test().assertValue(result)
  }

  @InternalCoroutinesApi
  @Test
  fun `Should be able to handle login error`() = runBlockingTest {
    val result = Result.error(message = "test error", data = null) as Result<Account>
    coEvery { repository.login("username", "password") } returns flow {
      emit(result)
    }
    underTest.login("username", "password")

    underTest.loginUserFlow.asLiveData().test().assertValue(result)
  }

  @Test
  fun `Should be able to handle login button state not enabled`() = runBlockingTest {
    underTest.setUsername("")
    underTest.setUsername("  ")
    underTest.isLoginButtonEnabled.asLiveData().test().assertValue(false)
  }

  @Test
  fun `Should be able to handle login button state enabled`() = runBlockingTest {
    underTest.setUsername("username")
    underTest.setUsername("password")
    underTest.isLoginButtonEnabled.asLiveData().test().assertValue(false)
  }
}
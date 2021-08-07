package com.percivalruiz.cartrack

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.jraska.livedata.test
import com.percivalruiz.cartrack.data.Account
import com.percivalruiz.cartrack.data.Result
import com.percivalruiz.cartrack.repository.LoginRepository
import com.percivalruiz.cartrack.repository.LoginStateRepository
import com.percivalruiz.cartrack.ui.login.LoginViewModel
import com.percivalruiz.cartrack.ui.splash.SplashViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*

@ExperimentalCoroutinesApi
class SplashViewModelTest {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  private val dispatcher = TestCoroutineDispatcher()

  private lateinit var underTest: SplashViewModel

  @MockK
  private lateinit var loginStateRepo: LoginStateRepository

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    Dispatchers.setMain(dispatcher)
    underTest = SplashViewModel(loginStateRepo)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
    dispatcher.cleanupTestCoroutines()
  }

  @Test
  fun `Should be able to get login status true`() {
    every { loginStateRepo.isLoggedIn() } returns true

    val result = underTest.isLoggedIn()

    Assert.assertEquals(result, true)
  }

  @Test
  fun `Should be able to get login status false`() {
    every { loginStateRepo.isLoggedIn() } returns false

    val result = underTest.isLoggedIn()

    Assert.assertEquals(result, false)
  }
}
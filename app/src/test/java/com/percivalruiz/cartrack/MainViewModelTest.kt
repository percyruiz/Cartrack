package com.percivalruiz.cartrack

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.percivalruiz.cartrack.repository.SessionRepository
import com.percivalruiz.cartrack.ui.splash.SplashViewModel
import com.percivalruiz.cartrack.ui.user.MainViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

@ExperimentalCoroutinesApi
class MainViewModelTest {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  private val dispatcher = TestCoroutineDispatcher()

  private lateinit var underTest: MainViewModel

  @MockK
  private lateinit var repo: SessionRepository

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    Dispatchers.setMain(dispatcher)
    underTest = MainViewModel(repo)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
    dispatcher.cleanupTestCoroutines()
  }

  @Test
  fun `Should be able to logout successfully`() {
    every { repo.setState(false) } just Runs

    underTest.logout()

    verify { repo.setState(false) }
  }
}
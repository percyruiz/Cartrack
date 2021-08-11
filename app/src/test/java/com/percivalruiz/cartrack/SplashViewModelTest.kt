package com.percivalruiz.cartrack

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.percivalruiz.cartrack.repository.SessionRepository
import com.percivalruiz.cartrack.ui.splash.SplashViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

@ExperimentalCoroutinesApi
class SplashViewModelTest {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  private val dispatcher = TestCoroutineDispatcher()

  private lateinit var underTest: SplashViewModel

  @MockK
  private lateinit var sessionRepo: SessionRepository

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    Dispatchers.setMain(dispatcher)
    underTest = SplashViewModel(sessionRepo)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
    dispatcher.cleanupTestCoroutines()
  }

  @Test
  fun `Should be able to get login status true`() {
    every { sessionRepo.isLoggedIn() } returns true

    val result = underTest.isLoggedIn()

    Assert.assertEquals(result, true)
  }

  @Test
  fun `Should be able to get login status false`() {
    every { sessionRepo.isLoggedIn() } returns false

    val result = underTest.isLoggedIn()

    Assert.assertEquals(result, false)
  }
}
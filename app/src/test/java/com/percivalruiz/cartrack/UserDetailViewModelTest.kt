package com.percivalruiz.cartrack

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import com.jraska.livedata.test
import com.percivalruiz.cartrack.data.User
import com.percivalruiz.cartrack.repository.SessionRepository
import com.percivalruiz.cartrack.repository.UserRepository
import com.percivalruiz.cartrack.ui.user.detail.UserDetailViewModel
import com.percivalruiz.cartrack.ui.user.detail.UserDetailViewModel.Companion.USER_ID
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*

@ExperimentalCoroutinesApi
class UserDetailViewModelTest {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  private val dispatcher = TestCoroutineDispatcher()

  private lateinit var underTest: UserDetailViewModel

  @MockK
  private lateinit var repo: UserRepository

  @MockK
  private lateinit var handle: SavedStateHandle

  @Before
  fun setup() {
    MockKAnnotations.init(this)
    Dispatchers.setMain(dispatcher)
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
    dispatcher.cleanupTestCoroutines()
  }

  @Test
  fun `Should be able to get user using user id`() {
    val user = createUser()
    coEvery { repo.getUser(1) } returns flow { emit(user) }

    every { handle.contains(USER_ID) } returns false

    every {
      handle.getLiveData<Long>(USER_ID)
    } returns MutableLiveData(1)

    every {
      handle.set(USER_ID, any<Long>())
    } just Runs

    underTest = UserDetailViewModel(handle, repo)
    underTest.getUser(1)

    underTest.user.asLiveData().test().assertValue(user)

  }

  private fun createUser(): User {
    return User(
      id = 1,
      name = "Percival Ruiz",
      username = "percivalruiz",
      email = "percivalruiz@gmail.com",
      address = User.Address(
        street = "Pines St.",
        suite = "Suit life on deck",
        city = "Mandaluyong",
        zipcode = "0000",
        geo = User.Address.Geo(
          lat = "-32.123",
          lng = "81.12313f"
        )
      ),
      phone = "1-770-736-8031 x56442",
      website = "something.com",
      company = User.Company(
        name = "CarTrack",
        catchPhrase = "Pls hire me",
        bs = "Cars?"
      )
    )
  }
}
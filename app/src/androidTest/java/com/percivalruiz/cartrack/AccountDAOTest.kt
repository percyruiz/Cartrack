package com.percivalruiz.cartrack

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.percivalruiz.cartrack.data.Account
import com.percivalruiz.cartrack.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AccountDAOTest {

  @get:Rule
  val instantExecutorRule = InstantTaskExecutorRule()

  private val dispatcher = TestCoroutineDispatcher()

  private lateinit var db: AppDatabase

  @Before
  fun setup() {
    Dispatchers.setMain(dispatcher)
    db = Room.inMemoryDatabaseBuilder(
      InstrumentationRegistry.getContext(),
      AppDatabase::class.java
    )
      .allowMainThreadQueries()
      .build()
  }

  @After
  fun tearDown() {
    Dispatchers.resetMain()
    db.close()
  }

  @Test
  fun insertAndGetAccount() = runBlockingTest {
    val account = Account(1, "username", "password")
    db.accountDAO().insert(account)
    val userDB = async {
      db.accountDAO().getAccount("username", "password")
    }

    userDB.await().let {
      Assert.assertEquals(account, it)
    }
  }
}

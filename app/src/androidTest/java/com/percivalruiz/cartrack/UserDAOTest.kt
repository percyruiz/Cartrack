package com.percivalruiz.cartrack
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.room.Room
//import androidx.test.InstrumentationRegistry
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import com.percivalruiz.cartrack.data.User
//import com.percivalruiz.cartrack.db.AppDatabase
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.async
//import kotlinx.coroutines.test.TestCoroutineDispatcher
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.runBlockingTest
//import kotlinx.coroutines.test.setMain
//import org.junit.*
//import org.junit.runner.RunWith
//
//@ExperimentalCoroutinesApi
//@RunWith(AndroidJUnit4::class)
//class UserDAOTest {
//
//  @get:Rule
//  val instantExecutorRule = InstantTaskExecutorRule()
//
//  private val dispatcher = TestCoroutineDispatcher()
//
//  private lateinit var db: AppDatabase
//
//  @Before
//  fun setup() {
//    Dispatchers.setMain(dispatcher)
//    db = Room.inMemoryDatabaseBuilder(
//      InstrumentationRegistry.getContext(),
//      AppDatabase::class.java
//    )
//      .allowMainThreadQueries()
//      .build()
//  }
//
//  @After
//  fun tearDown() {
//    Dispatchers.resetMain()
//    db.close()
//  }
//
//  @Test
//  fun insertAndGetAllUser() = runBlockingTest {
//    val user = createUser()
//    db.userDAO().insert(user)
//    val userDB = async {
//      db.userDAO().getAll()
//    }
//
//    userDB.await().let {
//      Assert.assertEquals(user, it[0])
//      Assert.assertEquals(1, it.size)
//    }
//  }
//
//  @Test
//  fun insertAndGetUserById() = runBlockingTest {
//    val user = createUser()
//    db.userDAO().insert(user)
//    val userDB = async {
//      db.userDAO().getById(1)
//    }
//
//    userDB.await().let {
//      Assert.assertEquals(user, it)
//    }
//  }
//
//  @Test
//  fun nukeUserDAO() = runBlockingTest {
//    val user = createUser()
//    db.userDAO().insert(user)
//    db.userDAO().nuke()
//    val userDB = async {
//      db.userDAO().getAll()
//    }
//
//    userDB.await().let {
//      Assert.assertEquals(0, it.size)
//    }
//  }
//
//  private fun createUser(): User {
//    return User(
//      id = 1,
//      name = "Percival Ruiz",
//      username = "percivalruiz",
//      email = "percivalruiz@gmail.com",
//      address = User.Address(
//        street = "Pines St.",
//        suite = "Suit life on deck",
//        city = "Mandaluyong",
//        zipcode = "0000",
//        geo = User.Address.Geo(
//          lat = "-32.123",
//          lng = "81.12313f"
//        )
//      ),
//      phone = "1-770-736-8031 x56442",
//      website = "something.com",
//      company = User.Company(
//        name = "CarTrack",
//        catchPhrase = "Pls hire me",
//        bs = "Cars?"
//      )
//    )
//  }
//}

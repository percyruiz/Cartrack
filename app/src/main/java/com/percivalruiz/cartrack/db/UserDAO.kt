package com.percivalruiz.cartrack.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.percivalruiz.cartrack.data.Account
import com.percivalruiz.cartrack.data.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(vararg user: User)

  @Query("SELECT * FROM user")
  fun getAll(): PagingSource<Int, User>

  @Query("SELECT * FROM user where id = :id")
  fun getById(id: Long): Flow<User?>

  @Query("DELETE FROM user")
  fun nuke()
}
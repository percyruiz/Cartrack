package com.percivalruiz.cartrack.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.percivalruiz.cartrack.data.Account

@Dao
interface AccountDAO {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(vararg account: Account)

  @Query("SELECT * FROM account where username = :username AND password = :password")
  fun getAccount(username: String, password: String): Account
}
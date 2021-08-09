package com.percivalruiz.cartrack.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.percivalruiz.cartrack.data.Account
import com.percivalruiz.cartrack.data.RemoteKey
import com.percivalruiz.cartrack.data.User

@Database(
  entities = [Account::class, User::class, RemoteKey::class],
  version = 1,
  exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
  abstract fun accountDAO(): AccountDAO
  abstract fun userDAO(): UserDAO
  abstract fun remoteKeyDAO(): RemoteKeyDAO
}

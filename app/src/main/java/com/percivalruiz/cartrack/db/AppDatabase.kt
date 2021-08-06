package com.percivalruiz.cartrack.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.percivalruiz.cartrack.data.Account

@Database(
  entities = [Account::class],
  version = 1,
  exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
  abstract fun accountDAO(): AccountDAO
}

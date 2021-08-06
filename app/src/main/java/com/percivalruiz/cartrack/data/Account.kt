package com.percivalruiz.cartrack.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

/**
 * Represents user accounts
 */
@JsonClass(generateAdapter = true)
@Entity(tableName = "account")
data class Account (
  @PrimaryKey(autoGenerate = true) val id: Long? = null,

  @ColumnInfo(name = "username")
  val username: String?,

  @ColumnInfo(name = "password")
  val password: String?,
)
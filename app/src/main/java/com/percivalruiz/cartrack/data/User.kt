package com.percivalruiz.cartrack.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

/**
 * Represents response from https://jsonplaceholder.typicode.com/users?
 */
@JsonClass(generateAdapter = true)
@Entity(tableName = "user")
data class User (
  @PrimaryKey val id: Long,
  val name: String,
  val username: String,
  val email: String,
  @Embedded val address: Address,
  val phone: String,
  val website: String,
  @Embedded val company: Company
) {

  @JsonClass(generateAdapter = true)
  @Entity(tableName = "address")
  data class Address (
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    @Embedded val geo: Geo
  ) {

    @JsonClass(generateAdapter = true)
    @Entity(tableName = "geo")
    data class Geo (
      val lat: String,
      val lng: String
    )
  }

  @JsonClass(generateAdapter = true)
  @Entity(tableName = "company")
  data class Company (
    @ColumnInfo(name = "company_name") val name: String,
    val catchPhrase: String,
    val bs: String
  )
}

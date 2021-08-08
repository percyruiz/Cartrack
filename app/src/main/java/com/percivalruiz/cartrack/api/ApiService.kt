package com.percivalruiz.cartrack.api

import com.percivalruiz.cartrack.data.User
import com.percivalruiz.cartrack.util.NETWORK_ITEM_SIZE
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

  @GET("/users")
  suspend fun getUsers(
    @Query("_start") start: Int?,
    @Query("_limit") limit: Int = NETWORK_ITEM_SIZE
  ): List<User>
}
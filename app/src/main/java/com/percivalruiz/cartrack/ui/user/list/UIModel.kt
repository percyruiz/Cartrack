package com.percivalruiz.cartrack.ui.user.list

import com.percivalruiz.cartrack.data.User

sealed class UIModel {
  class UserItem(val user: User): UIModel()
  class SeparatorItem(val tag: String): UIModel()
}
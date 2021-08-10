package com.percivalruiz.cartrack.ui.user.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.percivalruiz.cartrack.R
import com.percivalruiz.cartrack.data.User

/**
 * Populates the User list item
 */
class UserViewHolder(
  view: View,
  onClick: (id: Long) -> Unit,
) : RecyclerView.ViewHolder(view) {

  private var user: User? = null
  private val name: TextView = view.findViewById(R.id.name)
  private val username: TextView = view.findViewById(R.id.username)
  private val email: TextView = view.findViewById(R.id.email)
  private val phone: TextView = view.findViewById(R.id.phone)

  init {
    view.setOnClickListener {
      onClick.invoke(user?.id ?: 0)
    }
  }

  fun bind(user: User?) {
    this.user = user
    user?.apply {
      this@UserViewHolder.name.text = name
      this@UserViewHolder.username.text = username
      this@UserViewHolder.email.text = email
      this@UserViewHolder.phone.text = phone
    }
  }

  companion object {
    fun create(parent: ViewGroup, onClick: (id: Long) -> Unit): UserViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
      return UserViewHolder(view, onClick)
    }
  }
}
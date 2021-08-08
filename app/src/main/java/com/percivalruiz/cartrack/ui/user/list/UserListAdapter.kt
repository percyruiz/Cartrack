package com.percivalruiz.cartrack.ui.user.list

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.percivalruiz.cartrack.data.User

/**
 * Adapter for showing Users
 * Extends [PagingDataAdapter] that handles paging using [Paging3] library
 */
class UserListAdapter(
  private val onClick: (id: Long) -> Unit
) : PagingDataAdapter<User, UserViewHolder>(ITEM_COMP) {

  override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
    return UserViewHolder.create(parent, onClick)
  }

  companion object {
    private val ITEM_COMP = object : DiffUtil.ItemCallback<User>() {
      override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
        oldItem == newItem
    }
  }
}
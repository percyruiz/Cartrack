package com.percivalruiz.cartrack.ui.user.list

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.percivalruiz.cartrack.R
import com.percivalruiz.cartrack.data.User

/**
 * Adapter for showing Users
 * Extends [PagingDataAdapter] that handles paging using [Paging3] library
 */
class UserListAdapter(
  var onClick: (id: Long) -> Unit
) : PagingDataAdapter<UIModel, RecyclerView.ViewHolder>(ITEM_COMP) {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return if (viewType == R.layout.user_item) {
      UserViewHolder.create(parent, onClick)
    } else {
      SeparatorViewHolder.create(parent)
    }
  }

  override fun getItemViewType(position: Int): Int {
    return when (getItem(position)) {
      is UIModel.UserItem -> R.layout.user_item
      is UIModel.SeparatorItem -> R.layout.separator_item
      null -> throw UnsupportedOperationException("Unknown view")
    }
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    val model = getItem(position)
    model.let {
      when (model) {
        is UIModel.UserItem -> (holder as UserViewHolder).bind(model.user)
        is UIModel.SeparatorItem -> {
        }
      }
    }
  }

  companion object {
    private val ITEM_COMP = object : DiffUtil.ItemCallback<UIModel>() {
      override fun areItemsTheSame(oldItem: UIModel, newItem: UIModel): Boolean {
        return (oldItem is UIModel.UserItem && newItem is UIModel.UserItem &&
            oldItem.user.id == newItem.user.id) ||
            (oldItem is UIModel.SeparatorItem && newItem is UIModel.SeparatorItem &&
                oldItem.tag == newItem.tag)
      }

      override fun areContentsTheSame(oldItem: UIModel, newItem: UIModel): Boolean =
        oldItem == newItem
    }
  }
}
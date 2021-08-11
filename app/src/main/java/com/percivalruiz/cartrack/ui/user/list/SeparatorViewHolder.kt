package com.percivalruiz.cartrack.ui.user.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.percivalruiz.cartrack.R

class SeparatorViewHolder(view: View): RecyclerView.ViewHolder(view) {

  companion object {
    fun create(parent: ViewGroup): SeparatorViewHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.separator_item, parent, false)
      return SeparatorViewHolder(view)
    }
  }
}
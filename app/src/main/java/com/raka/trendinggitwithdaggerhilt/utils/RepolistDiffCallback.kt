package com.raka.trendinggitwithdaggerhilt.utils

import androidx.recyclerview.widget.DiffUtil
import com.raka.trendinggitwithdaggerhilt.data.model.compact.ItemsCompact

class RepolistDiffCallback:DiffUtil.ItemCallback<ItemsCompact>() {
    override fun areItemsTheSame(oldItem: ItemsCompact, newItem: ItemsCompact): Boolean {
        return oldItem.full_name == newItem.full_name
    }

    override fun areContentsTheSame(oldItem: ItemsCompact, newItem: ItemsCompact): Boolean {
        return oldItem == newItem
    }
}
package com.raka.trendinggitwithdaggerhilt.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.raka.trendinggitwithdaggerhilt.data.model.compact.ItemsCompact
import com.raka.trendinggitwithdaggerhilt.view.adapter.viewholders.RepoListViewHolder
import com.raka.trendinggitwithdaggerhilt.databinding.ViewRepoListItemBinding
import com.raka.trendinggitwithdaggerhilt.utils.RepolistDiffCallback

class RepoListAdapter():PagedListAdapter<ItemsCompact,RepoListViewHolder>(RepolistDiffCallback()) {
    var repoList:MutableList<ItemsCompact> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val dataBinding = ViewRepoListItemBinding.inflate(inflater,parent,false)
        return RepoListViewHolder(dataBinding)
    }

//    override fun getItemCount() = repoList.size

    override fun onBindViewHolder(holder: RepoListViewHolder, position: Int) {
        getItem(position).let { holder.setup(it!!) }
//        holder.setup(repoList[position])
    }

    fun updateRepoList(repoList:MutableList<ItemsCompact>){
        this.repoList.addAll(repoList)
        notifyDataSetChanged()
    }
}
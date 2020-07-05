package com.raka.myapplication.domain

import com.raka.myapplication.data.model.Item
import com.raka.trendinggitwithdaggerhilt.data.model.compact.ItemsCompact
import com.raka.trendinggitwithdaggerhilt.data.model.local.ItemsLocal

class RepoListMapper {
    fun convertRemoteToCompact(listRemote:List<Item>):MutableList<ItemsCompact>{
        val listCompact:MutableList<ItemsCompact> = mutableListOf()
        listRemote.forEach { item->
            listCompact.add(
                ItemsCompact(item.owner.avatar_url,item.html_url,item.full_name,item.description
            ,item.forks_count,item.stargazers_count,item.open_issues_count)
            )
        }
        return listCompact
    }
    fun convertRemoteToLocal(listRemote:List<Item>):MutableList<ItemsLocal>{
        val listCompact:MutableList<ItemsLocal> = mutableListOf()
        listRemote.forEach { item->
            listCompact.add(
                ItemsLocal(0,item.owner.avatar_url,item.html_url,item.full_name,item.description
                ,item.forks_count,item.stargazers_count,item.open_issues_count)
            )
        }
        return listCompact
    }
    fun convertLocaltoCompact(item: ItemsLocal)= ItemsCompact(item.avatar_url,item.html_url,item.full_name,item.description
                ,item.forks_count,item.stargazers_count,item.open_issues_count)
}
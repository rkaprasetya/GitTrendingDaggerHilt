package com.raka.trendinggitwithdaggerhilt.domain

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.raka.myapplication.data.model.GitResponse
import com.raka.trendinggitwithdaggerhilt.data.model.compact.ItemsCompact
import com.raka.trendinggitwithdaggerhilt.data.model.local.ItemsLocal
import com.raka.trendinggitwithdaggerhilt.utils.StatePagedList
import kotlinx.coroutines.flow.Flow

//interface RepoRepository {
//    suspend fun getRepoListFromServer():Flow<GitResponse>
//    suspend fun insertRepoListToDb(data:List<ItemsLocal>)
//    suspend fun getRepoListLocalData():List<ItemsLocal>
//     fun loadPagedList(): LiveData<PagedList<ItemsCompact>>
//    fun getStateFromPagedList():LiveData<StatePagedList>
//    fun loadPagedRepoLocal(): DataSource.Factory<Int,ItemsLocal>
//}
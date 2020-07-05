package com.raka.trendinggitwithdaggerhilt.domain

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.raka.trendinggitwithdaggerhilt.data.model.compact.ItemsCompact
import com.raka.trendinggitwithdaggerhilt.data.model.local.ItemsLocal
import com.raka.trendinggitwithdaggerhilt.utils.StatePagedList
import kotlinx.coroutines.flow.Flow

//interface RepoListUsecase {
//    suspend fun getRepoListRemote(): Flow<List<ItemsCompact>>
//    fun getPagedList(): LiveData<PagedList<ItemsCompact>>
//    fun getStatePagedList():LiveData<StatePagedList>
//    suspend fun repoListLocal():List<ItemsCompact>
//    suspend fun getPagedRepoLocal(): DataSource.Factory<Int, ItemsCompact>
//}
package com.raka.trendinggitwithdaggerhilt.domain

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.raka.trendinggitwithdaggerhilt.data.model.compact.ItemsCompact
import com.raka.myapplication.domain.RepoListMapper
import com.raka.trendinggitwithdaggerhilt.data.RepoRepository
import com.raka.trendinggitwithdaggerhilt.data.model.local.ItemsLocal
import com.raka.trendinggitwithdaggerhilt.utils.StatePagedList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject
interface RepoListUsecase {
    suspend fun getRepoListRemote(): Flow<List<ItemsCompact>>
    fun getPagedList(): LiveData<PagedList<ItemsCompact>>
    fun getStatePagedList():LiveData<StatePagedList>
    suspend fun repoListLocal():List<ItemsCompact>
    suspend fun getPagedRepoLocal(): DataSource.Factory<Int, ItemsCompact>
}
class RepoListUsecaseImpl @Inject constructor (private val repoListRepository: RepoRepository, private val mapper: RepoListMapper):RepoListUsecase {
//    private val mapper = RepoListMapper()
    override suspend fun getRepoListRemote():Flow<List<ItemsCompact>> {
       val response = repoListRepository.getRepoListFromServer()
        response.collect { data->
            repoListRepository.insertRepoListToDb(mapper.convertRemoteToLocal(data.items))
        }
        return response.map {
            mapper.convertRemoteToCompact(it.items)
        }
    }
    override fun getPagedList() = repoListRepository.loadPagedList()
    override fun getStatePagedList() = repoListRepository.getStateFromPagedList()
    override suspend fun repoListLocal() = repoListRepository.getRepoListLocalData().map { mapper.convertLocaltoCompact(it) }
    override suspend fun getPagedRepoLocal(): DataSource.Factory<Int, ItemsCompact> = repoListRepository.loadPagedRepoLocal().map {  mapper.convertLocaltoCompact(it) }


}
package com.raka.trendinggitwithdaggerhilt.domain

import com.raka.myapplication.data.model.compact.ItemsCompact
import com.raka.myapplication.domain.RepoListMapper
import com.raka.myapplication.repository.RepoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepoListUsecase @Inject constructor (private val repoListRepository: RepoRepository,private val mapper: RepoListMapper) {
//    private val mapper = RepoListMapper()
    suspend fun getRepoListRemote():Flow<List<ItemsCompact>> {
       val response = repoListRepository.getRepoListFromServer()
        response.collect { data->
            repoListRepository.insertRepoListToDb(mapper.convertRemoteToLocal(data.items))
        }
        return response.map {
            mapper.convertRemoteToCompact(it.items)
        }
    }

//    fun getRepoListRemote():Single<List<ItemsCompact>> = repoListRepository.getRepoListFromServer()
//        .doAfterSuccess { repoListRepository.insertRepoListToDb(mapper.convertRemoteToLocal(it.items)) }
//        .map { mapper.convertRemoteToCompact(it.items) }

    suspend fun repoListLocal() = repoListRepository.getRepoListLocalData().map { mapper.convertLocaltoCompact(it) }



}
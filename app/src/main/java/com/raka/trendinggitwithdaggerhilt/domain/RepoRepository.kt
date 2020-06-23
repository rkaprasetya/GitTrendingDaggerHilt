package com.raka.myapplication.repository

import androidx.lifecycle.LiveData
import com.raka.myapplication.data.model.GitResponse
import com.raka.myapplication.data.model.local.ItemsLocal
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    suspend fun getRepoListFromServer():Flow<GitResponse>
    suspend fun insertRepoListToDb(data:List<ItemsLocal>)
    suspend fun getRepoListLocalData():List<ItemsLocal>
}
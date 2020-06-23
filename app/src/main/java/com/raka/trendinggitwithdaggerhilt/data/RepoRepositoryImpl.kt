package com.raka.trendinggitwithdaggerhilt.data

import android.content.Context
import com.raka.myapplication.data.api.ApiClient
import com.raka.myapplication.data.api.ApiService
import com.raka.myapplication.data.database.AppDatabase
import com.raka.myapplication.data.database.ParametersDao
import com.raka.myapplication.data.model.GitResponse
import com.raka.myapplication.data.model.local.ItemsLocal
import com.raka.myapplication.repository.RepoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(private val dao: ParametersDao,private val service:ApiService): RepoRepository {

//    var dao: ParametersDao = AppDatabase.getInstance(context).parametersDao()

    fun getRepoList(onResult: (isSuccess: Boolean, response: GitResponse?) -> Unit) {
        service.getRepo().enqueue(object : Callback<GitResponse> {
            override fun onFailure(call: Call<GitResponse>, t: Throwable) {
                onResult(false, null)
            }

            override fun onResponse(call: Call<GitResponse>, response: Response<GitResponse>) {
                if (response.isSuccessful) {
                    onResult(true, response.body()!!)
                } else {
                    onResult(false, null)
                }
            }
        })
    }

//    companion object {
//        private var INSTANCE: RepoRepositoryImpl? = null
//        fun getInstance(context: Context) = INSTANCE
//            ?: RepoRepositoryImpl(context)
//                .also {
//            INSTANCE = it
//        }
//    }

//    override fun getRepoListFromServer(): Single<GitResponse> {
//        return ApiClient.instance.getRepoRx()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//    }

    override suspend fun getRepoListFromServer(): Flow<GitResponse> {
        return flow {
            val data = service.getRepoRx()
            emit(data)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun insertRepoListToDb(data: List<ItemsLocal>) {
            dao.deleteRepolist()
            dao.insertRepolist(data)
    }

    override suspend fun getRepoListLocalData() = dao.getLiveRepoList()
}
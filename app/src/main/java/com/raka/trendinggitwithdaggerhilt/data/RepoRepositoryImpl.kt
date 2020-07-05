package com.raka.trendinggitwithdaggerhilt.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.raka.trendinggitwithdaggerhilt.data.api.ApiService
import com.raka.myapplication.data.database.ParametersDao
import com.raka.myapplication.data.model.GitResponse
import com.raka.trendinggitwithdaggerhilt.data.model.compact.ItemsCompact
import com.raka.trendinggitwithdaggerhilt.data.model.local.ItemsLocal
import com.raka.trendinggitwithdaggerhilt.data.datasource.DataSourceFactory
import com.raka.trendinggitwithdaggerhilt.utils.StatePagedList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
interface RepoRepository {
    suspend fun getRepoListFromServer():Flow<GitResponse>
    suspend fun insertRepoListToDb(data:List<ItemsLocal>)
    suspend fun getRepoListLocalData():List<ItemsLocal>
    fun loadPagedList(): LiveData<PagedList<ItemsCompact>>
    fun getStateFromPagedList():LiveData<StatePagedList>
    fun loadPagedRepoLocal(): DataSource.Factory<Int,ItemsLocal>
}
class RepoRepositoryImpl @Inject constructor(private val dao: ParametersDao,private val service: ApiService):
    RepoRepository {

//    var dao: ParametersDao = AppDatabase.getInstance(context).parametersDao()
    val dataSourceFactory =  DataSourceFactory(service)
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
    override  fun loadPagedList():LiveData<PagedList<ItemsCompact>>{
         val config = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(10)
        .build()
        return LivePagedListBuilder(dataSourceFactory,config).build()
    }

    override fun getStateFromPagedList(): LiveData<StatePagedList> {
        return dataSourceFactory.liveDataState
    }

    override fun loadPagedRepoLocal(): DataSource.Factory<Int,ItemsLocal> = dao.getPagedRepoList()

    override suspend fun getRepoListFromServer(): Flow<GitResponse> {
        return flow {
            val data = service.getRepoRx(page = 1)
            emit(data)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun insertRepoListToDb(data: List<ItemsLocal>) {
            dao.deleteRepolist()
            dao.insertRepolist(data)
    }

    override suspend fun getRepoListLocalData() = dao.getLiveRepoList()
}
package com.raka.trendinggitwithdaggerhilt.data.datasource

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.raka.myapplication.data.database.ParametersDao
import com.raka.trendinggitwithdaggerhilt.data.model.compact.ItemsCompact
import com.raka.myapplication.domain.RepoListMapper
import com.raka.trendinggitwithdaggerhilt.data.api.ApiService
import com.raka.trendinggitwithdaggerhilt.utils.StatePagedList
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class RepolistDataSource (private val service: ApiService
):PageKeyedDataSource<Int, ItemsCompact>() {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.Main + job)
    private var _networkState : MutableLiveData<StatePagedList> = MutableLiveData(StatePagedList.idle())
    val networkState : LiveData<StatePagedList>
    get() = _networkState
    private val mapper =RepoListMapper()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ItemsCompact>
    ) {
        scope.launch {
            try {
                flow { emit(service.getRepoRx2(itemsPerPage = params.requestedLoadSize, page = 1)) }
                    .onStart {
                    _networkState.value = StatePagedList.loading()
                       }
                    .collect { data ->
                    val compact = mapper.convertRemoteToCompact(data.items)
                        _networkState.value = StatePagedList.success()
                        callback.onResult(compact, null, 1 + 1)
                }
            }catch (e:Exception){
                setError(e)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ItemsCompact>) {
        scope.launch {
            try {
                flow{
                    emit(service.getRepoRx2(itemsPerPage = params.requestedLoadSize, page = params.key))
                }
                    .onStart {
                        _networkState.value = StatePagedList.loading()
                    }
                    .collect { data ->
                    val key = params.key + 1
                        _networkState.value = StatePagedList.success()
                    callback.onResult(mapper.convertRemoteToCompact(data.items), key
                    )
                    }
            }catch (e:Exception){
                setError(e)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ItemsCompact>) {
        scope.launch {
            try {
                flow { emit(service.getRepoRx2(itemsPerPage = params.requestedLoadSize, page = params.key)) }
                    .onStart {
                        _networkState.value = StatePagedList.loading() }
                    .collect { data ->
                    val key = if (params.key > 1) params.key - 1 else 0
                        _networkState.value = StatePagedList.success()
                    callback.onResult(mapper.convertRemoteToCompact(data.items), key)
                    }
            }catch (e:Exception){
                setError(e)
            }
        }
    }
    private fun setError(e:Exception){
        _networkState.value = StatePagedList.error(e.message?:"Error getting data")
        Log.e("error","Load after error message ${e.message}")
    }
    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}
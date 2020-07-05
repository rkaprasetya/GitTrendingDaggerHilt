package com.raka.trendinggitwithdaggerhilt.view.ui.repolist

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.raka.trendinggitwithdaggerhilt.data.model.compact.ItemsCompact
import com.raka.trendinggitwithdaggerhilt.domain.RepoListUsecaseImpl
import com.raka.trendinggitwithdaggerhilt.utils.Resource
import com.raka.trendinggitwithdaggerhilt.utils.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepoListViewModel @ViewModelInject constructor (private val repoListUsecase: RepoListUsecaseImpl) : ViewModel(){
//    private val repoListUseCase = RepoListUsecase(RepoRepositoryImpl())
    private val _repoListCompact = MutableLiveData<Resource<List<ItemsCompact>>>()
    private val _pageListRepoData = MutableLiveData<Resource<PagedList<ItemsCompact>>>()
    var pagedListRepo :LiveData<PagedList<ItemsCompact>> = repoListUsecase.getPagedList()
    var stateProcess = repoListUsecase.getStatePagedList()

    private fun loadRepoList(){
        viewModelScope.launch {
            _repoListCompact.value = Resource.loading(null)
            val isInternetAvailable = withContext(Dispatchers.IO){
                Util.isInternetAvailable()
            }
            if(!isInternetAvailable){
                val factory : DataSource.Factory<Int,ItemsCompact> = repoListUsecase.getPagedRepoLocal()
                val pagedListBuilder:LivePagedListBuilder<Int,ItemsCompact> = LivePagedListBuilder<Int,ItemsCompact>(factory,10)
                pagedListRepo = pagedListBuilder.build()
            }else{
            }
        }
    }

    private fun loadRemoteData(){
        viewModelScope.launch {
            repoListUsecase.getRepoListRemote()
                .onStart {  _repoListCompact.value = Resource.loading(null)
                    Log.e("onStart","Loading State")
                }
                .catch { error-> Log.e("Error","Server error message= ${error.message}")
                    _repoListCompact.value = Resource.error("${error.message}",null)
                    loadLocalData()
                }
                .collect { data->
                    if (data.isNullOrEmpty()){
                        _repoListCompact.value = Resource.error("Fail loading data",null)
                    }else{
                        _repoListCompact.value = Resource.success(data)
                    }
                }
        }
    }

    private fun loadLocalData(){
       viewModelScope.launch {
           repoListUsecase.repoListLocal().let {
               if(it.isNullOrEmpty()){
                   _repoListCompact.value = Resource.error("Fail loading data",null)
               }else{
                   _repoListCompact.value = Resource.success(it)
               }
           }
       }
    }
}
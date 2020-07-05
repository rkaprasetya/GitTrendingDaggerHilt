package com.raka.trendinggitwithdaggerhilt.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.raka.myapplication.data.database.ParametersDao
import com.raka.trendinggitwithdaggerhilt.data.api.ApiService
import com.raka.trendinggitwithdaggerhilt.data.model.compact.ItemsCompact

class DataSourceFactory (service: ApiService) : DataSource.Factory<Int, ItemsCompact>(){
    val liveDataSource = MutableLiveData<RepolistDataSource>()
    val repolistDataSource=RepolistDataSource(service)
    var liveDataState = repolistDataSource.networkState

    override fun create(): DataSource<Int, ItemsCompact> {
        liveDataSource.postValue(repolistDataSource)
        return repolistDataSource
    }
}
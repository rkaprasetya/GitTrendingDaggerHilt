package com.raka.myapplication.data.database

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raka.trendinggitwithdaggerhilt.data.model.local.ItemsLocal

@Dao
interface ParametersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insertRepolist(data:List<ItemsLocal>)

    @Query("DELETE FROM repositorylist")
    suspend fun deleteRepolist()

    @Query("SELECT * from repositorylist")
    suspend fun getLiveRepoList(): List<ItemsLocal>

    @Query("SELECT * from repositorylist")
    fun getPagedRepoList():DataSource.Factory<Int,ItemsLocal>
}
package com.raka.trendinggitwithdaggerhilt.data.api

import com.raka.myapplication.data.model.GitResponse
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/repositories")
    fun getRepo(@Query("q") search: String = "trending", @Query("sort") sort: String = "stars"): Call<GitResponse>

//    @GET("search/repositories")
//    suspend fun getRepoRx(@Query("q") search: String = "trending", @Query("sort") sort: String = "stars",@Query("page") page:Int = 3): GitResponse

    @GET("search/repositories")
    suspend fun getRepoRx(@Query("q") search: String = "trending", @Query("sort") sort: String = "stars",
                          @Query("per_page")itemsPerPage:Int=10,@Query("page") page:Int): GitResponse
    @GET("search/repositories")
    suspend fun getRepoRx2(@Query("q") search: String = "trending", @Query("sort") sort: String = "stars",
                          @Query("per_page")itemsPerPage:Int=10,@Query("page") page:Int): GitResponse
}
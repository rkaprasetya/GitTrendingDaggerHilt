package com.raka.trendinggitwithdaggerhilt.di

import com.raka.trendinggitwithdaggerhilt.data.api.ApiService
import com.raka.myapplication.data.database.ParametersDao
import com.raka.myapplication.domain.RepoListMapper
import com.raka.trendinggitwithdaggerhilt.data.RepoRepository
import com.raka.trendinggitwithdaggerhilt.data.RepoRepositoryImpl
import com.raka.trendinggitwithdaggerhilt.domain.RepoListUsecase
import com.raka.trendinggitwithdaggerhilt.domain.RepoListUsecaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class ActivityModules {
    @Provides
    @ActivityScoped
    fun provideRepolistUsecase(repoRepository: RepoRepository, mapper: RepoListMapper):RepoListUsecase= RepoListUsecaseImpl(repoRepository,mapper)

    @Provides
    @ActivityScoped
    fun provideRepolistRepository(dao:ParametersDao,service: ApiService): RepoRepository =RepoRepositoryImpl(dao,service)

    @Provides
    @ActivityScoped
    fun provideRepolistMapper():RepoListMapper=RepoListMapper()
}
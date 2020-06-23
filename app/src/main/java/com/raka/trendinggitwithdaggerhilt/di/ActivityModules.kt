package com.raka.trendinggitwithdaggerhilt.di

import com.raka.myapplication.data.api.ApiService
import com.raka.myapplication.data.database.ParametersDao
import com.raka.myapplication.domain.RepoListMapper
import com.raka.myapplication.repository.RepoRepository
import com.raka.trendinggitwithdaggerhilt.data.RepoRepositoryImpl
import com.raka.trendinggitwithdaggerhilt.domain.RepoListUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class ActivityModules {
    @Provides
    @ActivityRetainedScoped
    fun provideRepolistUsecase(repoRepository: RepoRepository,mapper: RepoListMapper):RepoListUsecase= RepoListUsecase(repoRepository,mapper)

    @Provides
    @ActivityRetainedScoped
    fun provideRepolistRepository(dao:ParametersDao,service:ApiService):RepoRepository=RepoRepositoryImpl(dao,service)

    @Provides
    @ActivityRetainedScoped
    fun provideRepolistMapper():RepoListMapper=RepoListMapper()
}
package com.sensehawk.mobile_ui.injection.module

import com.nhaarman.mockito_kotlin.mock
import com.sensehawk.data.repository.ProjectsRemote
import com.sensehawk.mobile_ui.BuildConfig
import com.sensehawk.remote.service.GithubTrendingService
import com.sensehawk.remote.service.GithubTrendingServiceFactory
import dagger.Module
import dagger.Provides

@Module
object TestRemoteModule {

    @Provides
    @JvmStatic
    fun providesGithubTrendingService(): GithubTrendingService {
        return GithubTrendingServiceFactory.makeGithubTrendingService(BuildConfig.DEBUG)
    }

    @Provides
    @JvmStatic
    fun bindProjectsCache(): ProjectsRemote {
        return mock()
    }
}
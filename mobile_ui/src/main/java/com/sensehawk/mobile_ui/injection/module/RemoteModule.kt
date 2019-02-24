package com.sensehawk.mobile_ui.injection.module

import com.sensehawk.data.repository.ProjectsRemote
import com.sensehawk.mobile_ui.BuildConfig
import com.sensehawk.remote.ProjectRemoteImpl
import com.sensehawk.remote.service.GithubTrendingService
import com.sensehawk.remote.service.GithubTrendingServiceFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class RemoteModule {

  @Module
  companion object {

    @Provides
    @JvmStatic
    fun providesGithubTrendingService(): GithubTrendingService {
      return GithubTrendingServiceFactory.makeGithubTrendingService(BuildConfig.DEBUG)
    }

  }

  @Binds
  abstract fun bindProjectsCache(projectCacheImpl: ProjectRemoteImpl): ProjectsRemote
}
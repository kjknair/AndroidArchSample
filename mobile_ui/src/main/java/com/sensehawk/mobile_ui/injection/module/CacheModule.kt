package com.sensehawk.mobile_ui.injection.module

import android.app.Application
import com.sensehawk.cache.ProjectCacheImpl
import com.sensehawk.cache.db.ProjectsDatabase
import com.sensehawk.data.repository.ProjectsCache
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class CacheModule {

  @Module
  companion object {

    @Provides
    @JvmStatic
    fun providesDatabase(application: Application): ProjectsDatabase {
      return ProjectsDatabase.getInstance(application)
    }

  }

  @Binds
  abstract fun bindProjectsCache(projectCacheImpl: ProjectCacheImpl): ProjectsCache

}
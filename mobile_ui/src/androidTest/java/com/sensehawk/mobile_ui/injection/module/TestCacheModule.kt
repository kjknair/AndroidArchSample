package com.sensehawk.mobile_ui.injection.module

import android.app.Application
import com.nhaarman.mockito_kotlin.mock
import com.sensehawk.cache.db.ProjectsDatabase
import com.sensehawk.data.repository.ProjectsCache
import dagger.Module
import dagger.Provides

@Module
object TestCacheModule {

    @Provides
    @JvmStatic
    fun providesDatabase(application: Application): ProjectsDatabase {
        return ProjectsDatabase.getInstance(application)
    }

    @Provides
    @JvmStatic
    fun bindProjectsCache(): ProjectsCache {
        return mock()
    }

}
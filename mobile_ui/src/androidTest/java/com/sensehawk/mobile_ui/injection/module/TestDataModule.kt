package com.sensehawk.mobile_ui.injection.module

import com.nhaarman.mockito_kotlin.mock
import com.sensehawk.domain.repository.ProjectRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestDataModule {

    @Provides
    @Singleton
    @JvmStatic
    fun bindDataRepository(): ProjectRepository {
        return mock()
    }

}
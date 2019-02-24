package com.sensehawk.mobile_ui.injection.module

import com.sensehawk.data.ProjectsDataRepository
import com.sensehawk.domain.repository.ProjectRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

  @Binds
  abstract fun bindDataRepository(dataRepository: ProjectsDataRepository): ProjectRepository

}
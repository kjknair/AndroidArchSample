package com.sensehawk.mobile_ui.injection

import android.app.Application
import com.sensehawk.domain.repository.ProjectRepository
import com.sensehawk.mobile_ui.injection.module.*
import com.sensehawk.mobile_ui.test.TestApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class
        , TestApplicationModule::class
        , TestCacheModule::class
        , TestDataModule::class
        , TestRemoteModule::class
        , PresentationModule::class
        , UiModule::class
    ]
)
interface TestApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): TestApplicationComponent.Builder

        fun build(): TestApplicationComponent

    }

    fun inject(application: TestApplication)

    fun projectRepository(): ProjectRepository

}

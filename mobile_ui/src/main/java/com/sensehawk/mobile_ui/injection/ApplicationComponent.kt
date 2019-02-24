package com.sensehawk.mobile_ui.injection

import android.app.Application
import com.sensehawk.mobile_ui.GithubTrendingApplication
import com.sensehawk.mobile_ui.injection.module.ApplicationModule
import com.sensehawk.mobile_ui.injection.module.CacheModule
import com.sensehawk.mobile_ui.injection.module.DataModule
import com.sensehawk.mobile_ui.injection.module.PresentationModule
import com.sensehawk.mobile_ui.injection.module.RemoteModule
import com.sensehawk.mobile_ui.injection.module.UiModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
      AndroidInjectionModule::class,
      ApplicationModule::class,
      UiModule::class,
      PresentationModule::class,
      DataModule::class,
      CacheModule::class,
      RemoteModule::class
    ]
)
interface ApplicationComponent {

  fun inject(application: GithubTrendingApplication)

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(application: Application): Builder

    fun build(): ApplicationComponent

  }

}

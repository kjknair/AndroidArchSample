package com.sensehawk.mobile_ui.injection.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.sensehawk.mobile_ui.injection.ViewModelFactory
import com.sensehawk.presentation.BrowseBookmarkViewModel
import com.sensehawk.presentation.BrowseProjectsViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class PresentationModule {

  @Binds
  @IntoMap
  @ViewModelKey(BrowseProjectsViewModel::class)
  abstract fun bindsBrowseProjectsViewModel(viewModel: BrowseProjectsViewModel): ViewModel

  @Binds
  @IntoMap
  @ViewModelKey(BrowseBookmarkViewModel::class)
  abstract fun bindsBrowseBookmarkViewModel(viewModel: BrowseBookmarkViewModel): ViewModel

  @Binds
  abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val key: KClass<out ViewModel>)
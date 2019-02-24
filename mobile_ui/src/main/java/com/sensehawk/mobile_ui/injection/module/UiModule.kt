package com.sensehawk.mobile_ui.injection.module

import com.sensehawk.domain.executor.PostExecutionThread
import com.sensehawk.mobile_ui.UiThread
import com.sensehawk.mobile_ui.bookmarked.BookmarkedActivity
import com.sensehawk.mobile_ui.browse.BrowseActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UiModule {

    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributesBrowseActivity(): BrowseActivity

    @ContributesAndroidInjector
    abstract fun contributesBookmarkedActivity(): BookmarkedActivity

}
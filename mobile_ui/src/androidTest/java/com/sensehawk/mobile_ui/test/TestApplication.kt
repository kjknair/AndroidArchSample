package com.sensehawk.mobile_ui.test

import android.app.Activity
import android.app.Application
import android.support.test.InstrumentationRegistry
import com.sensehawk.mobile_ui.injection.DaggerTestApplicationComponent
import com.sensehawk.mobile_ui.injection.TestApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class TestApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    lateinit var appComponent: TestApplicationComponent

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityInjector
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerTestApplicationComponent.builder()
            .application(this)
            .build()
         appComponent.inject(this)
    }

    companion object {
        fun appComponent(): TestApplicationComponent {
            return (InstrumentationRegistry.getTargetContext().applicationContext as TestApplication)
                .appComponent
        }
    }

}
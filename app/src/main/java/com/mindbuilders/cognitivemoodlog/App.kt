package com.mindbuilders.cognitivemoodlog

import com.mindbuilders.cognitivemoodlog.data.LogRepository
import com.mindbuilders.cognitivemoodlog.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject


class App : DaggerApplication() {

    @Inject
    lateinit var logRepository: LogRepository

    private lateinit var dispatchingAndroidInjector: AndroidInjector<out DaggerApplication>
    override fun onCreate() {
        val component = DaggerAppComponent.builder()
            .context(this)
            .build()
        this.dispatchingAndroidInjector = component
        component.inject(this)
        super.onCreate()

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return dispatchingAndroidInjector
    }
}


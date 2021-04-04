package com.mindbuilders.cognitivemoodlog

import com.mindbuilders.cognitivemoodlog.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector

import javax.inject.Inject

class App: DaggerApplication() {

    lateinit var dispatchingAndroidInjector: AndroidInjector<out DaggerApplication>

    override fun onCreate() {
        val component = DaggerAppComponent.builder()
            .context(this)
            .build()
        this.dispatchingAndroidInjector = component
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return dispatchingAndroidInjector
    }
}
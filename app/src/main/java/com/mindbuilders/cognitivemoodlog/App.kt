package com.mindbuilders.cognitivemoodlog

import com.mindbuilders.cognitivemoodlog.data.LegacyDbMigrator
import com.mindbuilders.cognitivemoodlog.data.LogRepository
import com.mindbuilders.cognitivemoodlog.di.DaggerAppComponent
import com.mindbuilders.cognitivemoodlog.model.*
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


class App : DaggerApplication() {

    @Inject
    lateinit var logRepository: LogRepository

    @Inject
    lateinit var legacyDbMigrator: LegacyDbMigrator

    lateinit var dispatchingAndroidInjector: AndroidInjector<out DaggerApplication>

    val migrationScope = CoroutineScope(Dispatchers.IO)
    override fun onCreate() {
        val component = DaggerAppComponent.builder()
            .context(this)
            .build()
        this.dispatchingAndroidInjector = component
        component.inject(this)
        super.onCreate()
        migrationScope.launch {
            legacyDbMigrator.migrateDbIfNecessary()
        }

    }

    override fun onTerminate() {
        super.onTerminate()
        migrationScope.cancel()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return dispatchingAndroidInjector
    }
}


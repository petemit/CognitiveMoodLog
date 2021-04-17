package com.mindbuilders.cognitivemoodlog

import com.mindbuilders.cognitivemoodlog.data.LegacyDbMigrator
import com.mindbuilders.cognitivemoodlog.data.LogRepository
import com.mindbuilders.cognitivemoodlog.data.MigrationMediator
import com.mindbuilders.cognitivemoodlog.di.DaggerAppComponent
import com.mindbuilders.cognitivemoodlog.model.*
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn
import java.util.*
import javax.inject.Inject


class App : DaggerApplication() {

    @Inject
    lateinit var logRepository: LogRepository

    @Inject
    lateinit var legacyDbMigrator: LegacyDbMigrator

    @Inject
    lateinit var migrationMediator: MigrationMediator

    lateinit var dispatchingAndroidInjector: AndroidInjector<out DaggerApplication>
    lateinit var job: Job

    private val migrationScope = CoroutineScope(Dispatchers.IO)
    override fun onCreate() {
        val component = DaggerAppComponent.builder()
            .context(this)
            .build()
        this.dispatchingAndroidInjector = component
        component.inject(this)
        super.onCreate()


        job = launchMigrationJob()

        migrationScope.launch {
            migrationMediator.password.collect {
                if (it.isNotEmpty() && !migrationMediator.enterPasswordState.value) {
                    job = launchMigrationJob()
                }
            }

        }
        migrationScope.launch {
            migrationMediator.enterPasswordState.collect {
                if (it) {
                    job.cancel()
                }
            }
        }

    }

    fun launchMigrationJob() = migrationScope.launch {
        legacyDbMigrator.migrateDbIfNecessary()
    }

    override fun onTerminate() {
        super.onTerminate()
        migrationScope.cancel()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return dispatchingAndroidInjector
    }
}


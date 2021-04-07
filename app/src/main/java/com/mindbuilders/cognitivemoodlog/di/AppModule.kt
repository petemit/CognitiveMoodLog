package com.mindbuilders.cognitivemoodlog.di

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import com.mindbuilders.cognitivemoodlog.view.MainActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

@Module(includes = [AndroidInjectionModule::class])
interface AppModule {

    @ExperimentalFoundationApi
    @ContributesAndroidInjector
    fun mainActivity(): MainActivity

    companion object {
        @Provides
        fun providesRealmConfig(context: Context): RealmConfiguration {
            Realm.init(context)

            return RealmConfiguration.Builder()
                //todo PLEASE REMOVE
                .deleteRealmIfMigrationNeeded()
                .build()
        }


        @Provides
        fun providesRealm(realmConfig: RealmConfiguration): Realm {
            return Realm.getInstance(realmConfig)
        }
    }
}
package com.mindbuilders.cognitivemoodlog.di

import android.content.Context
import com.mindbuilders.cognitivemoodlog.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent: AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder
        fun build(): AppComponent
    }
}
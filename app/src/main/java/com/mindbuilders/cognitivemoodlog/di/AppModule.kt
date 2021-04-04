package com.mindbuilders.cognitivemoodlog.di

import androidx.compose.foundation.ExperimentalFoundationApi
import com.mindbuilders.cognitivemoodlog.view.MainActivity
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.ContributesAndroidInjector

@Module(includes = [AndroidInjectionModule::class])
interface AppModule {

    @ExperimentalFoundationApi
    @ContributesAndroidInjector
    fun mainActivity(): MainActivity

}
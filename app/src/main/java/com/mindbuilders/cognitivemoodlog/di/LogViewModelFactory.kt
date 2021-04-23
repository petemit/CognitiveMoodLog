package com.mindbuilders.cognitivemoodlog.di

import androidx.lifecycle.SavedStateHandle
import com.mindbuilders.cognitivemoodlog.data.LogRepository
import com.mindbuilders.cognitivemoodlog.data.MigrationMediator
import com.mindbuilders.cognitivemoodlog.view.LogViewModel
import dagger.Lazy
import io.realm.Realm
import javax.inject.Inject


class LogViewModelFactory @Inject constructor(private val repository: LogRepository, private val realm: Lazy<Realm>, private val migrationMediator: MigrationMediator) {

    fun create(handle: SavedStateHandle): LogViewModel {
        return LogViewModel(repository = repository, realm = realm, handle = handle, migrationMediator = migrationMediator)
    }
}
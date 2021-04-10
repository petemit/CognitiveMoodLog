package com.mindbuilders.cognitivemoodlog.di

import androidx.lifecycle.SavedStateHandle
import com.mindbuilders.cognitivemoodlog.data.SeedDataRepository
import com.mindbuilders.cognitivemoodlog.view.LogViewModel
import dagger.Lazy
import io.realm.Realm
import javax.inject.Inject


class LogViewModelFactory @Inject constructor(private val repository: SeedDataRepository, private val realm: Lazy<Realm>) {

    fun create(handle: SavedStateHandle): LogViewModel {
        return LogViewModel(repository = repository, realm = realm, handle = handle)
    }
}
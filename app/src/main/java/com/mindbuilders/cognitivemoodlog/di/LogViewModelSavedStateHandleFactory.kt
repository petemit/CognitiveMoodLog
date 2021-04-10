package com.mindbuilders.cognitivemoodlog.di

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

class LogViewModelSavedStateHandleFactory<out T : ViewModel> constructor(
    private val viewModelFactoryAssistant: LogViewModelFactory,
    owner: SavedStateRegistryOwner
) : AbstractSavedStateViewModelFactory(owner, null) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return viewModelFactoryAssistant.create(handle) as T
    }
}
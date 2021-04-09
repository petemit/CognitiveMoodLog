package com.mindbuilders.cognitivemoodlog.di

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.mindbuilders.cognitivemoodlog.view.LogViewModel

class LogViewModelSavedStateHandleFactory<out T : ViewModel> constructor(
    private val viewModelFactoryAssistant: ILogViewModelAssistantFactory<LogViewModel>,
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
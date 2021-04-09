package com.mindbuilders.cognitivemoodlog.di

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

interface ILogViewModelAssistantFactory<T: ViewModel> {
    fun create(handle: SavedStateHandle) : T
}
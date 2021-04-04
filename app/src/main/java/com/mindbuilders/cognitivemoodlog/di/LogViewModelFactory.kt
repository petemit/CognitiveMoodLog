package com.mindbuilders.cognitivemoodlog.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mindbuilders.cognitivemoodlog.view.LogViewModel
import javax.inject.Inject


class LogViewModelFactory @Inject constructor(val logViewModel: LogViewModel): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        //todo is this the only way to do this?
        return logViewModel as T
    }
}
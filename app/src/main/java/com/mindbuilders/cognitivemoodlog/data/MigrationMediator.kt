package com.mindbuilders.cognitivemoodlog.data

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MigrationMediator @Inject constructor(){
    val waitStatus = MutableStateFlow(false)
    val enterPasswordState = MutableStateFlow(false)
    val password = MutableStateFlow("")
}

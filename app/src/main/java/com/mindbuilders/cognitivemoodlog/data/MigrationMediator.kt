package com.mindbuilders.cognitivemoodlog.data

import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MigrationMediator @Inject constructor(){
    val migrationStatus = MutableStateFlow(false)
    val enterPasswordPrompt = MutableStateFlow(false)
    val needsRetry = MutableStateFlow(false)
    val password = MutableStateFlow("")
}

package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import com.mindbuilders.cognitivemoodlog.model.LogEntry
import com.mindbuilders.cognitivemoodlog.view.components.AppScaffold
import com.mindbuilders.cognitivemoodlog.view.components.TitleText

@Composable
fun ReviewLogs(navController: NavHostController, viewModel: LogViewModel) {
    viewModel.refreshLogEntries()
    val entries: List<LogEntry>? = viewModel.logEntries.observeAsState(listOf()).value
    AppScaffold(
        title = "See Past Logs",
        navController = navController,
        viewModel = viewModel,
        nextButton = {}) {
        LazyColumn {
            item {
                TitleText("Review Past Logs")
            }
            entries?.let {
                items(it) { entry ->
                    Text(entry.situation)
                }
            }
        }
    }
}
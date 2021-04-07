package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import com.mindbuilders.cognitivemoodlog.view.components.AppScaffold
import com.mindbuilders.cognitivemoodlog.view.components.TitleText

@Composable
fun SeePastLogs(navController: NavHostController, viewModel: LogViewModel) {
//    val entries by viewModel.getLogEntries().observeAsState(emptyList())
   AppScaffold(title = "See Past Logs", navController = navController, viewModel = viewModel, nextButton = {}) {
       LazyColumn {
           item {
               TitleText("Review Past Logs")
           }
//           items(entries) {
//
//           }
       }
   }
}
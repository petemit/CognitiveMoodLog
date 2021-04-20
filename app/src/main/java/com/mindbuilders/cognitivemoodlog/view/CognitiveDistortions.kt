package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.mindbuilders.cognitivemoodlog.model.Thought
import com.mindbuilders.cognitivemoodlog.nav.Screen
import com.mindbuilders.cognitivemoodlog.ui.AppScaffold
import com.mindbuilders.cognitivemoodlog.ui.CbtDivider
import com.mindbuilders.cognitivemoodlog.ui.TitleText

@Composable
fun CognitiveDistortions(navController: NavController, viewModel: LogViewModel) {
    val thoughts: List<Thought> by viewModel.thoughts.observeAsState(emptyList())
    val allCogged: Boolean by viewModel.allCogged.observeAsState(false)
    AppScaffold(
        "Cognitive Distortions",
        destination = Screen.ThoughtsAfter,
        destEnabled = allCogged,
        instructions = "Select a cognitive distortion for each thought or pick \"I'm not sure\"",
        navController = navController,
        viewModel = viewModel
    ) {
        Column {
            TitleText("Which Cognitive Distortion Matches That Negative Thought Most Accurately?")
            CbtDivider()
            LazyColumn {
                items(thoughts, key = { item: Thought -> item.id }) { thought ->
                    ThoughtAnalysisRow(thought = thought, viewModel = viewModel)
                }
            }
        }
    }
}



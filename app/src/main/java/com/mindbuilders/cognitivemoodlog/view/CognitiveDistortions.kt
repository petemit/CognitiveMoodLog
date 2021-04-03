package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.mindbuilders.cognitivemoodlog.model.Thought
import com.mindbuilders.cognitivemoodlog.view.components.AppScaffold
import com.mindbuilders.cognitivemoodlog.view.components.CbtButton
import com.mindbuilders.cognitivemoodlog.view.components.CbtDivider
import com.mindbuilders.cognitivemoodlog.view.components.TitleText

@Composable
fun CognitiveDistortions(navController: NavController, viewModel: LogViewModel) {
    val thoughts: List<Thought> by viewModel.thoughts.observeAsState(emptyList())
    AppScaffold("Cognitive Distortions",
        destination = Screen.ThoughtsAfter,
        destEnabled = true,
        navController = navController) {
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



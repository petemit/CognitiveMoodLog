package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.mindbuilders.cognitivemoodlog.view.components.AppScaffold
import com.mindbuilders.cognitivemoodlog.view.components.TitleText
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.navigate
import com.mindbuilders.cognitivemoodlog.model.Thought
import com.mindbuilders.cognitivemoodlog.view.components.CbtButton
import com.mindbuilders.cognitivemoodlog.view.components.CbtDivider

@Composable
fun CognitiveDistortions(navController: NavController, viewModel: LogViewModel) {
    val thoughts: List<Thought> = viewModel.thoughts.observeAsState(listOf()).value
    AppScaffold("Cognitive Distortions") {
        Column {
            TitleText("Which Cognitive Distortion Matches That Negative Thought Most Accurately?")
            CbtButton(name = "Next", modifier = Modifier
                .fillMaxWidth(.5f)
                .padding(bottom = 8.dp)) {
                navController.navigate(Screen.ThoughtsAfter.route)
            }
            CbtDivider()
            LazyColumn {
                items(thoughts, key = { item: Thought -> item.id }) { thought ->
                    ThoughtAnalysisRow(thought = thought, viewModel = viewModel)
                }
            }
        }
    }
}



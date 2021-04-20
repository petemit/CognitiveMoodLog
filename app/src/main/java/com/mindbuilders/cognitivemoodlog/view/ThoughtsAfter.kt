package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mindbuilders.cognitivemoodlog.model.Thought
import com.mindbuilders.cognitivemoodlog.nav.Screen
import com.mindbuilders.cognitivemoodlog.ui.AppScaffold
import com.mindbuilders.cognitivemoodlog.ui.CbtDivider
import com.mindbuilders.cognitivemoodlog.ui.TitleText


@Composable
fun ThoughtsAfter(navController: NavController, viewModel: LogViewModel) {
    val thoughtList: List<Thought> by viewModel.thoughts.observeAsState(listOf())
    val hasPositiveThoughts: Boolean by viewModel.hasPositiveThoughts.observeAsState(false)

    AppScaffold("Positive Thoughts",
        destination = Screen.ThoughtsReview,
        destEnabled = hasPositiveThoughts,
        instructions = "Write a positive thought for each negative thought to continue",
        navController = navController,
        viewModel = viewModel
    ) {
        LazyColumn {
            item {
                TitleText("What are some logical, positive, and reasonable thoughts that compete directly with your negative thoughts?\n\nNext, rate the positive thought's strength from 0-10")
                CbtDivider()
            }
            items(thoughtList, key = { item: Thought -> item.id }) { thought ->
                ThoughtRow(thought, false, viewModel = viewModel)
            }
            item {
                Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
            }
        }
    }
}

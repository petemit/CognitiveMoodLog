package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import com.mindbuilders.cognitivemoodlog.model.Thought
import com.mindbuilders.cognitivemoodlog.view.components.AppScaffold
import com.mindbuilders.cognitivemoodlog.view.components.CbtDivider
import com.mindbuilders.cognitivemoodlog.view.components.TitleText


@Composable
fun ThoughtsAfter(navController: NavController, viewModel: LogViewModel) {
    val thoughtList: List<Thought> by viewModel.thoughts.observeAsState(listOf())
    val hasPositiveThoughts: Boolean by viewModel.hasPositiveThoughts.observeAsState(false)

    AppScaffold("Positive Thoughts",
        destination = Screen.ThoughtsReview,
        destEnabled = hasPositiveThoughts,
        instructions = "Write a positive thought for each negative thought to continue",
        navController = navController
    ) {
        LazyColumn {
            item {
                TitleText("What are some logical, positive, and reasonable thoughts that compete directly with your negative thoughts?\n\nNext, rate the positive thought's strength from 0-10")
                CbtDivider()
            }
            items(thoughtList, key = { item: Thought -> item.id }) { thought ->
                ThoughtRow(thought, false, viewModel = viewModel)
            }
        }
    }
}

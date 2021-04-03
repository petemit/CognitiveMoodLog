package com.mindbuilders.cognitivemoodlog.view

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
fun ThoughtsAfter(navController: NavController, viewModel: LogViewModel) {
    val thoughtList: List<Thought> by viewModel.thoughts.observeAsState(listOf())

    AppScaffold("Positive Thoughts") {
        LazyColumn {
            item {
                TitleText("What are some logical, positive, and reasonable thoughts that compete directly with your negative thoughts?\n\nNext, rate the positive thought's strength from 0-10")
                CbtButton(
                    name = "Next", modifier = Modifier
                        .fillMaxWidth(.5f)
                        .padding(bottom = 8.dp)
                ) {
                    navController.navigate(Screen.ThoughtsReview.route)
                }
                CbtDivider()
            }
            items(thoughtList, key = { item: Thought -> item.id }) { thought ->
                ThoughtRow(thought, false, viewModel = viewModel)
            }
        }
    }
}

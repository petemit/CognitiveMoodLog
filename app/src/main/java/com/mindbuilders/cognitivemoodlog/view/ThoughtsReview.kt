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
import com.mindbuilders.cognitivemoodlog.view.components.ScrollableSituation
import com.mindbuilders.cognitivemoodlog.view.components.TitleText

@Composable
fun ThoughtsReview(navController: NavController, viewModel: LogViewModel) {
    val situation: String by viewModel.situation.observeAsState("")
    val thoughtList: List<Thought> by viewModel.thoughts.observeAsState(listOf())
    AppScaffold("Thoughts Review") {
        LazyColumn {
            item {
                TitleText("Now, how much do you believe your previous negative thoughts?")
                ScrollableSituation(situation = situation)
                CbtButton(
                    name = "Next", modifier = Modifier
                        .fillMaxWidth(.5f)
                        .padding(bottom = 8.dp)
                ) {
                    navController.navigate(Screen.EmotionsAfter.route)
                }
            }

            items(thoughtList, key = { thought: Thought -> thought.id }) { thought ->
                AfterAnalysisRow(
                    before = thought.negBeliefBefore,
                    after = thought.negBeliefAfter,
                    text = thought.thoughtBefore,
                    isReview = false
                ) {
                    viewModel.editThought {
                        thought.negBeliefAfter = it
                    }
                }

            }
        }
    }
}
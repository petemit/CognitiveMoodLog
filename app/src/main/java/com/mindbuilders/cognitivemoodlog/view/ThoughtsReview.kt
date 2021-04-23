package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mindbuilders.cognitivemoodlog.model.Thought
import com.mindbuilders.cognitivemoodlog.nav.Screen
import com.mindbuilders.cognitivemoodlog.ui.AppScaffold
import com.mindbuilders.cognitivemoodlog.ui.SituationText
import com.mindbuilders.cognitivemoodlog.ui.TitleText

@Composable
fun ThoughtsReview(navController: NavController, viewModel: LogViewModel) {
    val situation: String by viewModel.situation.observeAsState("")
    val thoughtList: List<Thought> by viewModel.thoughts.observeAsState(listOf())
    val situationDialog = remember { mutableStateOf(false) }
    AppScaffold("Thoughts Review",
        destination = Screen.EmotionsAfter,
        destEnabled = true,
        navController = navController,
        viewModel = viewModel
    ) {
        LazyColumn(modifier = Modifier.padding(horizontal = 8.dp)) {
            item {
                TitleText("Now, how much do you believe your previous negative thoughts?")
                SituationText(situation = situation, situationDialog)
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
package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.mindbuilders.cognitivemoodlog.model.Emotion
import com.mindbuilders.cognitivemoodlog.model.Thought
import com.mindbuilders.cognitivemoodlog.view.components.AppScaffold
import com.mindbuilders.cognitivemoodlog.view.components.ScrollableSituation
import com.mindbuilders.cognitivemoodlog.view.components.TitleText


@Composable
fun LogReview(navController: NavController, viewModel: LogViewModel) {
    val situation: String by viewModel.situation.observeAsState("")
    val selectedEmotions: List<Emotion>? by viewModel.selectedEmotions.observeAsState()
    val thoughtList: List<Thought> by viewModel.thoughts.observeAsState(listOf())

    AppScaffold("Log Entry Review") {
        LazyColumn {
            item {
                TitleText("Review Your Log Entry.")
                ScrollableSituation(situation = situation)
            }

            item {
                TitleText("Emotions")
            }
            selectedEmotions?.let {
                items(it, key = { emotion: Emotion -> emotion.id }) { emotion ->
                    AfterAnalysisRow(
                        before = emotion.strengthBefore,
                        after = emotion.strengthAfter,
                        text = emotion.name,
                        true
                    ) {}
                }
            }

            item {
                TitleText("Thoughts")
            }
            items(thoughtList) {
                ThoughtRow(thought = it, viewModel = viewModel, isReview = true )
            }

        }
    }
}
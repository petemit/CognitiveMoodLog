package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.mindbuilders.cognitivemoodlog.model.Emotion
import com.mindbuilders.cognitivemoodlog.model.Thought
import com.mindbuilders.cognitivemoodlog.view.components.AppScaffold
import com.mindbuilders.cognitivemoodlog.view.components.MenuAction
import com.mindbuilders.cognitivemoodlog.ui.SituationText
import com.mindbuilders.cognitivemoodlog.ui.TitleText


@Composable
fun LogReview(navController: NavController, viewModel: LogViewModel,) {
    val situation: String by viewModel.situation.observeAsState("")
    val selectedEmotions: List<Emotion>? by viewModel.selectedEmotions.observeAsState()
    val thoughtList: List<Thought> by viewModel.thoughts.observeAsState(listOf())
    var situationDialog = remember { mutableStateOf(false) }

    AppScaffold("Log Entry Review",
        //destination = Screen.ThoughtsBefore,
        destEnabled = true,
        navController = navController,
        viewModel = viewModel,
        barActionBehavior = MenuAction.SAVE
    ) {
        val title = "Review Your Log Entry."
        ShowEmotionAndThoughtReview(title, situation, selectedEmotions, thoughtList, viewModel, situationDialog)
    }
}

@Composable
fun ShowEmotionAndThoughtReview(
    title: String,
    situation: String,
    selectedEmotions: List<Emotion>?,
    thoughtList: List<Thought>,
    viewModel: LogViewModel,
    situationDialog: MutableState<Boolean>? = null
) {
    LazyColumn {
        item {
            TitleText(title)
            if (situationDialog != null) {
                SituationText(situation = situation, situationDialog = situationDialog)
            }
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
            ThoughtRow(
                thought = it,
                isReview = true,
                viewModel = viewModel
            )
        }

    }
}
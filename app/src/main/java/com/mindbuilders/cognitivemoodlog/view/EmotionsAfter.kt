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
import com.mindbuilders.cognitivemoodlog.model.Emotion
import com.mindbuilders.cognitivemoodlog.view.components.AppScaffold
import com.mindbuilders.cognitivemoodlog.view.components.SituationText
import com.mindbuilders.cognitivemoodlog.view.components.TitleText

@Composable
fun EmotionsAfter(navController: NavController, viewModel: LogViewModel) {
    val situation: String by viewModel.situation.observeAsState("")
    val selectedEmotions: List<Emotion>? by viewModel.selectedEmotions.observeAsState()
    var situationDialog = remember { mutableStateOf(false) }
    AppScaffold(
        "Emotions After",
        destination = Screen.LogReview,
        destEnabled = true,
        navController = navController,
        viewModel = viewModel
    ) {
        LazyColumn(modifier = Modifier.padding(horizontal = 8.dp)) {
            item {
                TitleText("After this exercise, now rate the emotions you feel and rate their strengths from 1 to 10 in comparison to what they were before")
                SituationText(situation = situation, situationDialog)
            }
            selectedEmotions?.let {
                items(it, key = { emotion: Emotion -> emotion.id }) { emotion ->
                    AfterAnalysisRow(
                        before = emotion.strengthBefore,
                        after = emotion.strengthAfter,
                        text = emotion.name,
                        isReview = false
                    ) {
                        viewModel.editEmotion {
                            emotion.strengthAfter = it
                        }
                    }

                }
            }
        }
    }
}

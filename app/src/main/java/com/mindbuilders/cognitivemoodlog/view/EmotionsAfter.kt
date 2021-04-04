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
import com.mindbuilders.cognitivemoodlog.model.Emotion
import com.mindbuilders.cognitivemoodlog.util.roundTo
import com.mindbuilders.cognitivemoodlog.view.components.AppScaffold
import com.mindbuilders.cognitivemoodlog.view.components.CbtButton
import com.mindbuilders.cognitivemoodlog.view.components.ScrollableSituation
import com.mindbuilders.cognitivemoodlog.view.components.TitleText

@Composable
fun EmotionsAfter(navController: NavController, viewModel: LogViewModel) {
    val situation: String by viewModel.situation.observeAsState("")
    val selectedEmotions: List<Emotion>? by viewModel.selectedEmotions.observeAsState()
    AppScaffold(
        "Emotions After",
        destination = Screen.LogReview,
        destEnabled = true,
        navController = navController
    ) {
        LazyColumn(modifier = Modifier.padding(horizontal = 8.dp)) {
            item {
                TitleText("After this exercise, now rate the emotions you feel and rate their strengths from 1 to 10 in comparison to what they were before")
                ScrollableSituation(situation = situation)
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

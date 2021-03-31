package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.mindbuilders.cognitivemoodlog.model.Emotion
import com.mindbuilders.cognitivemoodlog.view.components.AppScaffold
import com.mindbuilders.cognitivemoodlog.view.components.CbtButton
import com.mindbuilders.cognitivemoodlog.view.components.ScrollableSituation
import com.mindbuilders.cognitivemoodlog.view.components.TitleText
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Slider
import com.mindbuilders.cognitivemoodlog.util.roundTo

@Composable
fun EmotionsAfter(navController: NavController, viewModel: LogViewModel) {
    val situation: String by viewModel.situation.observeAsState("")
    val selectedEmotions: List<Emotion>? = viewModel.selectedEmotions.observeAsState().value
    AppScaffold("Emotions After") {
        Column {

            TitleText("Pick the emotions you feel and rate their strengths from 1 to 10")
            ScrollableSituation(situation = situation)
            CbtButton(
                name = "Next", modifier = Modifier
                    .fillMaxWidth(.5f)
                    .padding(bottom = 8.dp)
            ) {
                navController.navigate(Screen.ThoughtsBefore.route)
            }
            selectedEmotions?.let {
                LazyColumn {
                    items(selectedEmotions, key = { emotion: Emotion -> emotion.id }) { emotion ->
                        AfterEmotionRow(emotion = emotion)
                    }
                }
            }
        }
    }
}
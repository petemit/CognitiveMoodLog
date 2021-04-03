package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
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
import com.mindbuilders.cognitivemoodlog.view.components.*

@ExperimentalFoundationApi
@Composable
fun EmotionsBefore(navController: NavController, viewModel: LogViewModel) {
    val situation: String by viewModel.situation.observeAsState("")
    val list: List<Emotion>? by viewModel.emotionList.observeAsState()
    val groupedEmotions by viewModel.groupedEmotions.observeAsState()
    AppScaffold("Pick Emotions") {
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
            list?.let {
                LazyColumn {
                    groupedEmotions?.forEach { (category, emotions) ->
                        stickyHeader {
                            Header(category)
                        }
                        items(emotions, key = { item: Emotion -> item.id }) { emotion ->
                            EmotionRow(emotion, true, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}

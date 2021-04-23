package com.mindbuilders.cognitivemoodlog.view


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import com.mindbuilders.cognitivemoodlog.model.Emotion
import com.mindbuilders.cognitivemoodlog.nav.Screen
import com.mindbuilders.cognitivemoodlog.ui.AppScaffold
import com.mindbuilders.cognitivemoodlog.ui.Header
import com.mindbuilders.cognitivemoodlog.ui.SituationText
import com.mindbuilders.cognitivemoodlog.ui.TitleText

@ExperimentalFoundationApi
@Composable
fun EmotionsBefore(navController: NavController, viewModel: LogViewModel) {
    val situation: String by viewModel.situation.observeAsState("")
    val list: List<Emotion>? by viewModel.emotionList.observeAsState()
    val groupedEmotions by viewModel.groupedEmotions.observeAsState()
    val selectedEmotions by viewModel.selectedEmotions.observeAsState()
    val situationDialog = remember { mutableStateOf(false) }

    AppScaffold(
        "Pick Emotions",
        destination = Screen.ThoughtsBefore,
        destEnabled = selectedEmotions?.isNotEmpty() ?: false,
        instructions = "Scroll to find the emotions you are feeling and drag at least one slider to continue",
        navController = navController,
        viewModel = viewModel
    ) {

        LazyColumn {
            item {
                TitleText("Pick the emotions you feel and rate their strengths from 1 to 10")
                SituationText(situation = situation, situationDialog)
            }
            list?.let {
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

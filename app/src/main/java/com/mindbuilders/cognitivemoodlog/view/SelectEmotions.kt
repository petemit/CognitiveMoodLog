package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate

@ExperimentalFoundationApi
@Composable
fun SelectEmotions(navController: NavController, viewModel: LogViewModel) {
    val situation: String by viewModel.situation.observeAsState("")
    AppScaffold("Pick Emotions") {
        Column {
            TitleText("Pick the emotions you feel and rate their strengths from 1 to 10")
            ScrollableSituation(situation = situation)
            NavigateButton(name = "Next", modifier = Modifier
                .fillMaxWidth(.5f)
                .padding(bottom = 8.dp)) {
                navController.navigate(Screen.ThoughtsBefore.route)
            }
            EmotionList(viewModel = viewModel, isBefore = true)
        }
    }
}

package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SelectEmotions(navController: NavController, viewModel: LogViewModel) {
    val situation: String by viewModel.situation.observeAsState("")
    AppScaffold("Pick Emotions") {
        Column {
            TitleText("Pick the emotions you feel and rate their strengths from 1 to 10")
            ScrollableSituation(situation = situation)
        }
    }
}

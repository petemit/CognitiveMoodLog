package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController

@Composable
fun ThoughtsBefore(navController: NavController, viewModel: LogViewModel) {
    val situation: String by viewModel.situation.observeAsState("")
    val thoughtBefore: String by viewModel.thoughtBefore.observeAsState("")
    AppScaffold("Pick Emotions") {
        Column {
            TitleText("Enter any negative thoughts that you automatically think of when you consider the situation")
            ScrollableSituation(situation = situation)
            Row {
                TextField(
                    value = thoughtBefore,
                    onValueChange = { viewModel.onThoughtBeforeChange(it) })
            }
        }
    }
}
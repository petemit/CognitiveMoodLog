package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun DescribeSituation(navController: NavController, viewModel: LogViewModel) {
    val situation: String by viewModel.situation.observeAsState("")
    AppScaffold {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(12.dp)
        ) {
            Text("Describe the situation that made you feel upset.")
            OutlinedTextField(
                value = situation,
                onValueChange = { string -> viewModel.onSituationChange(string) },
                modifier = Modifier.fillMaxWidth().padding(12.dp).fillMaxHeight(.5f)

            )
            NavigateButton(name = "Next", modifier = Modifier.fillMaxWidth(.5f)) {

            }
        }
    }
}

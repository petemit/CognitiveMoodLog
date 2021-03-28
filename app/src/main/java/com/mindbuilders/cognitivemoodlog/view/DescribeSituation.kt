package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.mindbuilders.cognitivemoodlog.ui.theme.CognitiveMoodLogTheme

@Composable
fun DescribeSituation(navController: NavController, viewModel: LogViewModel) {
    val situation: String by viewModel.situation.observeAsState("")
    AppScaffold("Describe Situation") {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(12.dp)
        ) {
            TitleText("Describe the situation that made you feel upset.")
            OutlinedTextField(
                value = situation,
                onValueChange = { string -> viewModel.onSituationChange(string) },
                modifier = Modifier.fillMaxWidth().padding(12.dp).fillMaxHeight(.5f)
            )
            NavigateButton(name = "Next", modifier = Modifier.fillMaxWidth(.5f)) {
                navController.navigate(Screen.SelectEmotions.route)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DescribeSituation_Preview() {
    val navController = rememberNavController()
    val viewModel = LogViewModel()
    CognitiveMoodLogTheme {
        DescribeSituation(navController = navController, viewModel = viewModel)
    }
}


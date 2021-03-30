package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.navigate
import com.mindbuilders.cognitivemoodlog.view.components.AppScaffold
import com.mindbuilders.cognitivemoodlog.view.components.CbtButton
import com.mindbuilders.cognitivemoodlog.view.components.ScrollableSituation
import com.mindbuilders.cognitivemoodlog.view.components.TitleText

@Composable
fun ThoughtsBefore(navController: NavController, viewModel: LogViewModel) {
    val situation: String by viewModel.situation.observeAsState("")
    var currentThought: String by rememberSaveable { mutableStateOf("") }

    AppScaffold("Pick Emotions") {
        Column {
            TitleText("Enter any negative thoughts that you automatically think of when you consider the situation.\n\nNext, rate the thought's strength from 0-10")
            ScrollableSituation(situation = situation)
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .weight(2f)
                        .padding(start = 12.dp, end = 12.dp),
                    value = currentThought,
                    label = { Text("Add Thought Here")},
                    onValueChange = { currentThought = it })
                CbtButton(
                    name = "Add", modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp, top = 8.dp)
                        .weight(1f)
                ) {
                    viewModel.addBeforeThought(currentThought)
                    currentThought = ""
                }
            }
            CbtButton(name = "Next", modifier = Modifier
                .fillMaxWidth(.5f)
                .padding(bottom = 8.dp)) {
                navController.navigate(Screen.CognitiveDistortions.route)
            }
            Divider(thickness = 1.dp, modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))
            ThoughtList(viewModel = viewModel, isBefore = true)
        }
    }
}
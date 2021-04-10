package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mindbuilders.cognitivemoodlog.model.Thought
import com.mindbuilders.cognitivemoodlog.ui.CbtButton
import com.mindbuilders.cognitivemoodlog.ui.CbtDivider
import com.mindbuilders.cognitivemoodlog.ui.SituationText
import com.mindbuilders.cognitivemoodlog.ui.TitleText
import com.mindbuilders.cognitivemoodlog.view.components.*

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ThoughtsBefore(navController: NavController, viewModel: LogViewModel) {
    val situation: String by viewModel.situation.observeAsState("")
    var currentThought: String by rememberSaveable { mutableStateOf("") }
    val thoughtList: List<Thought> by viewModel.thoughts.observeAsState(emptyList())
    val hasNegativeThought: Boolean by viewModel.hasANegativeThought.observeAsState(false)
    var thoughtCount: Int by rememberSaveable { mutableStateOf (thoughtList.count { it.thoughtBefore.isNotEmpty()}) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var situationDialog = remember { mutableStateOf(false) }

    AppScaffold(
        "Thoughts Before",
        destination = Screen.CognitiveDistortions,
        destEnabled = hasNegativeThought,
        instructions = "Type in a thought and click add to continue",
        navController = navController,
        viewModel = viewModel
    ) {
        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            item {
                TitleText("Enter any negative thoughts that you automatically think of when you consider the situation.\n\nNext, rate the thought's strength from 0-10")
                SituationText(situation = situation, situationDialog)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .weight(2f)
                            .padding(start = 12.dp, end = 12.dp),
                        value = currentThought,
                        label = { Text("Add Thought Here") },
                        onValueChange = {
                            currentThought = it
                        })
                    CbtButton(
                        name = "Add", modifier = Modifier
                            .padding(start = 12.dp, end = 12.dp, top = 8.dp)
                            .weight(1f)
                    ) {
                        viewModel.addBeforeThought(currentThought)
                        currentThought = ""
                        thoughtCount = viewModel.thoughts.value?.size ?: 0
                        keyboardController?.hideSoftwareKeyboard()
                    }
                }
                CbtDivider()
            }

            //todo this is a hack, but I'm not sure how to make thoughtList redraw.  I wonder if I have to have a local state that I need to handle separately.
            items(thoughtCount) { thought ->
            }
            items(thoughtList) {
                ThoughtRow(it, true, viewModel = viewModel)
            }
        }
    }
}
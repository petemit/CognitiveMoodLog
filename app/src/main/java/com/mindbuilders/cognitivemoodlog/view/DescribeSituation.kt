package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mindbuilders.cognitivemoodlog.nav.Screen
import com.mindbuilders.cognitivemoodlog.ui.AbandonDialog
import com.mindbuilders.cognitivemoodlog.ui.AppScaffold

import com.mindbuilders.cognitivemoodlog.ui.CbtButton
import com.mindbuilders.cognitivemoodlog.ui.TitleText

@Composable
fun DescribeSituation(navController: NavController, viewModel: LogViewModel) {
    val situation: String by viewModel.situation.observeAsState("")
    val abandonDialogIsShowing: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }

    AppScaffold(
        title = "Describe Situation",
        destination = Screen.SelectEmotions,
        destEnabled = situation.isNotEmpty(),
        instructions = "Fill out the situation to continue",
        navController = navController,
        backButton = {
            CbtButton(
                name = "Back", modifier = Modifier.fillMaxWidth()
            ) {
                abandonDialogIsShowing.value = true
            }
        },
        viewModel = viewModel
    ) {
        AbandonDialog(
            navController = navController,
            viewModel = viewModel,
            isShowing = abandonDialogIsShowing
        )
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(12.dp).fillMaxSize()
        ) {
            item {
                TitleText("Describe the situation that made you feel upset.")
                OutlinedTextField(
                    value = situation,
                    onValueChange = { string -> viewModel.onSituationChange(string) },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.*
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
import com.mindbuilders.cognitivemoodlog.view.components.AbandonDialog
import com.mindbuilders.cognitivemoodlog.view.components.AppScaffold
import com.mindbuilders.cognitivemoodlog.view.components.CbtButton
import com.mindbuilders.cognitivemoodlog.view.components.TitleText

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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(12.dp)
        ) {
            TitleText("Describe the situation that made you feel upset.")
            OutlinedTextField(
                value = situation,
                onValueChange = { string -> viewModel.onSituationChange(string) },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.5f)
            )
        }
    }
}


//todo I'm leaving this commented as some documentation... managing these preview functions is a pain as soon as I introduced Dagger.  Also, preview right now is so slow it is not buying me anything.  I'm removing the rest of them.
//@Preview(showBackground = true)
//@Composable
//fun DescribeSituation_Preview() {
//    val navController = rememberNavController()
//    val viewModel = LogViewModel(AssetFetcher(LocalContext.current))
//    CognitiveMoodLogTheme {
//        DescribeSituation(navController = navController, viewModel = viewModel)
//    }
//}


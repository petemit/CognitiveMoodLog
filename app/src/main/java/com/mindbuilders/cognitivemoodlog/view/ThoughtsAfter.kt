package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.mindbuilders.cognitivemoodlog.view.components.AppScaffold
import com.mindbuilders.cognitivemoodlog.view.components.CbtButton
import com.mindbuilders.cognitivemoodlog.view.components.CbtDivider
import com.mindbuilders.cognitivemoodlog.view.components.TitleText


@Composable
fun ThoughtsAfter(navController: NavController, viewModel: LogViewModel) {

    AppScaffold("Positive Thoughts") {
        Column {
            TitleText(  "What are some logical, positive, and reasonable thoughts that compete directly with your negative thoughts?\n\nNext, rate the positive thought's strength from 0-10")
            CbtButton(
                name = "Next", modifier = Modifier
                    .fillMaxWidth(.5f)
                    .padding(bottom = 8.dp)
            ) {
                navController.navigate(Screen.EmotionsAfter.route)
            }
            CbtDivider()
            ThoughtList(
                viewModel = viewModel,
                isBefore = false
            )
        }
    }
}

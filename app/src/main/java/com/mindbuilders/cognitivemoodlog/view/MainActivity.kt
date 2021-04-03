package com.mindbuilders.cognitivemoodlog.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.mindbuilders.cognitivemoodlog.ui.theme.CognitiveMoodLogTheme
import com.mindbuilders.cognitivemoodlog.view.components.AppScaffold
import com.mindbuilders.cognitivemoodlog.view.components.CbtButton

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: LogViewModel by viewModels()
        setContent {
            CognitiveMoodLogTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = Screen.MainMenu.route) {
                    composable(Screen.MainMenu.route) {
                        MainMenu(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    composable(Screen.DescribeSituation.route) {
                        DescribeSituation(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    composable(Screen.SelectEmotions.route) {
                        EmotionsBefore(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    composable(Screen.ThoughtsBefore.route) {
                        ThoughtsBefore(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    composable(Screen.CognitiveDistortions.route) {
                        CognitiveDistortions(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    composable(Screen.ThoughtsAfter.route) {
                        ThoughtsAfter(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    composable(Screen.EmotionsAfter.route) {
                        EmotionsAfter(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    composable(Screen.LogReview.route) {
                        LogReview(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                    composable(Screen.ThoughtsReview.route) {
                        ThoughtsReview(
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MainMenu(navController: NavController, viewModel: LogViewModel) {
    AppScaffold(
        "Cognitive Behavioral Therapy Pal",
        navController = navController
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(12.dp)
        ) {
            mainMenuDestinations.forEach { screen ->
                CbtButton(
                    name = stringResource(id = screen.resourceId), modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(.5f)
                ) {
                    navController.navigate(
                        screen.route
                    )
                }
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    val viewModel = LogViewModel()
    CognitiveMoodLogTheme {
        MainMenu(navController = navController, viewModel = viewModel)
    }
}


val mainMenuDestinations = listOf(
    Screen.DescribeSituation,
    Screen.ReviewLogs
)

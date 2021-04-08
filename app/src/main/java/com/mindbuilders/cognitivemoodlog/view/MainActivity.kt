package com.mindbuilders.cognitivemoodlog.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.mindbuilders.cognitivemoodlog.data.AssetFetcher
import com.mindbuilders.cognitivemoodlog.ui.theme.CognitiveMoodLogTheme
import com.mindbuilders.cognitivemoodlog.view.components.AppScaffold
import com.mindbuilders.cognitivemoodlog.view.components.CbtButton
import dagger.android.AndroidInjection
import com.mindbuilders.cognitivemoodlog.di.LogViewModelFactory
import com.mindbuilders.cognitivemoodlog.view.components.ScrollableText
import javax.inject.Inject

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: LogViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        val viewModel: LogViewModel by viewModels {
            viewModelFactory
        }
        setContent {
            val loadingState = viewModel.isLoading.observeAsState(initial = false)

            CognitiveMoodLogTheme {
                val navController = rememberNavController()
                buildNavHost(navController, viewModel)
            }
            if (loadingState.value) {
                ProgressView()
            }
        }
    }

    @Composable
    private fun buildNavHost(
        navController: NavHostController,
        viewModel: LogViewModel
    ) {
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
            composable(Screen.ReviewLogs.route) {
                ReviewLogs(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun MainMenu(navController: NavController, viewModel: LogViewModel) {
    AppScaffold(
        "Cognitive Behavioral Therapy Pal",
        navController = navController,
        viewModel = viewModel
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .fillMaxHeight()
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

@Composable
fun ProgressView() {

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            CircularProgressIndicator(modifier = Modifier.width(100.dp))
        }

    }

}


val mainMenuDestinations = listOf(
    Screen.DescribeSituation,
    Screen.ReviewLogs
)

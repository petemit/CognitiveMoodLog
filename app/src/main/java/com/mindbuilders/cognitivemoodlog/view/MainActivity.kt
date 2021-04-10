package com.mindbuilders.cognitivemoodlog.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mindbuilders.cognitivemoodlog.di.LogViewModelFactory
import com.mindbuilders.cognitivemoodlog.di.LogViewModelSavedStateHandleFactory
import com.mindbuilders.cognitivemoodlog.ui.theme.CognitiveMoodLogTheme
import com.mindbuilders.cognitivemoodlog.view.components.AppScaffold
import com.mindbuilders.cognitivemoodlog.ui.CbtButton
import dagger.android.AndroidInjection
import javax.inject.Inject

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: LogViewModelFactory
    val viewModel: LogViewModel by viewModels {
        LogViewModelSavedStateHandleFactory<LogViewModel>(viewModelFactory, this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        setContent {
            val loadingState = viewModel.isLoading.observeAsState(initial = false)
            val lastNav by viewModel.lastNav.observeAsState()
            CognitiveMoodLogTheme {
                val navController = rememberNavController()
                val lastNavVal = lastNav
                if (lastNavVal != null && lastNavVal.isNotEmpty()) {
                    BuildNavHost(navController, viewModel, lastNavVal)
                } else {
                    BuildNavHost(navController, viewModel)
                }
            }
            if (loadingState.value) {
                ProgressView()
            }
        }

    }

    @Composable
    private fun BuildNavHost(
        navController: NavHostController,
        viewModel: LogViewModel,
        lastNav: String = Screen.MainMenu.route
    ) {
        NavHost(navController, startDestination = lastNav) {
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
                    viewModel.nav(navController,screen.route)
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

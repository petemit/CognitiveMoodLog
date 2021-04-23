package com.mindbuilders.cognitivemoodlog.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.mindbuilders.cognitivemoodlog.di.LogViewModelFactory
import com.mindbuilders.cognitivemoodlog.di.LogViewModelSavedStateHandleFactory
import com.mindbuilders.cognitivemoodlog.nav.BuildNavHost
import com.mindbuilders.cognitivemoodlog.nav.mainMenuDestinations
import com.mindbuilders.cognitivemoodlog.ui.AppScaffold
import com.mindbuilders.cognitivemoodlog.ui.CbtButton
import com.mindbuilders.cognitivemoodlog.ui.MenuAction
import com.mindbuilders.cognitivemoodlog.ui.ProgressView
import com.mindbuilders.cognitivemoodlog.ui.theme.CognitiveMoodLogTheme
import dagger.android.AndroidInjection
import javax.inject.Inject

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: LogViewModelFactory
    val viewModel: LogViewModel by viewModels {
        LogViewModelSavedStateHandleFactory(viewModelFactory, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        setContent {
            val loadingState = viewModel.isLoading.observeAsState(initial = false)
            val passwordState = viewModel.passwordState.observeAsState(false)
            CognitiveMoodLogTheme {
                val navController = rememberNavController()
                navController.saveState()
                BuildNavHost(navController, viewModel)

                Column {
                    if (passwordState.value) {
                        EnterPassword(applicationContext, viewModel)
                    }
                    if (loadingState.value) {
                        ProgressView()
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
        navController = navController,
        viewModel = viewModel,
        barActionBehavior = MenuAction.ABOUT
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
                    text = stringResource(id = screen.resourceId), modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(.5f)
                ) {
                    navController.navigate(screen.route)
                }
            }
        }
    }


}



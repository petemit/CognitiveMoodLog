package com.mindbuilders.cognitivemoodlog.nav

import android.os.Bundle
import androidx.annotation.NonNull
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mindbuilders.cognitivemoodlog.view.*

@ExperimentalFoundationApi
@Composable
fun BuildNavHost(
    navController: NavHostController,
    viewModel: LogViewModel,
    lastNav: String = Screen.MainMenu.route
) {

    //This will dump the navigation state into a saved bundle in the view model and restore it by default
    val navState = remember { mutableStateOf(viewModel.lastNav.value) }
    DisposableEffect(key1 = navState ) {
       val callback: (NavController, NavDestination, Bundle?) -> Unit = { navController, _, _ ->
           val bundle = navController.saveState()
           navState.value = bundle ?: Bundle()
           if (bundle != null) {
               viewModel.updateLastNav(bundle)
           }
       }
        navController.addOnDestinationChangedListener(callback)

        onDispose {
            navController.removeOnDestinationChangedListener(callback)
        }
    }

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
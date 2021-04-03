package com.mindbuilders.cognitivemoodlog.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.mindbuilders.cognitivemoodlog.view.Screen

@Composable
fun AppScaffold(
    title: String,
    destination: Screen? = null,
    destEnabled: Boolean = false,
    navController: NavController,
    body: @Composable () -> Unit
) {
    Scaffold(
        topBar = { CbtBar(title) },
        bottomBar = {destination?.let {
            NavigationButtons(it, destEnabled, navController)
        } },
    ) {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp, 0.dp, 6.dp, 0.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Column(Modifier.wrapContentSize()
                    .padding(bottom = 50.dp)) {
                    body.invoke()
                }
            }
        }
    }
}

@Composable
fun NavigationButtons(destination: Screen, destEnabled: Boolean, navController: NavController) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier
            .padding(6.dp, 0.dp, 6.dp, 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            CbtButton(
                name = "Back", modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                navController.popBackStack()
            }
            CbtButton(
                name = "Next", modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
                    ,
                isEnabled = destEnabled
            ) {
                navController.navigate(destination.route)
            }

        }
    }
}
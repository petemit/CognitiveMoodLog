package com.mindbuilders.cognitivemoodlog.view.components

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.mindbuilders.cognitivemoodlog.view.LogViewModel
import com.mindbuilders.cognitivemoodlog.view.Screen

@Composable
fun AppScaffold(
    //todo this function is too big.  I'd like to DI some of these things, but how do I do that with a function?
    title: String,
    destination: Screen? = null,
    destEnabled: Boolean = false,
    navController: NavController,
    instructions: String = "",
    viewModel: LogViewModel,
    barActionBehavior: MenuAction = MenuAction.CLEAR,
    closeBehavior: () -> Unit = {

    },
    backButton: @Composable () -> Unit = {
        CbtButton(
            name = "Back", modifier = Modifier.fillMaxWidth()
        ) {
            navController.popBackStack()
        }
    },
    nextButton: @Composable () -> Unit = {
        CbtButton(
            name = "Next", modifier = Modifier.fillMaxWidth(),
            isEnabled = destEnabled
        ) {
            destination?.route?.let {
                viewModel.nav(navController, it)
            }
        }
    },
    body: @Composable () -> Unit
) {
    val context = LocalContext.current
    val abandonDialogIsShowing: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }
    AbandonDialog(
        navController = navController,
        viewModel = viewModel,
        isShowing = abandonDialogIsShowing
    )
    Scaffold(
        topBar = {
            when (barActionBehavior) {
                MenuAction.CLEAR -> {
                    CbtBar(
                        title
                    )
                    {
                        abandonDialogIsShowing.value = true
                    }

                }
                MenuAction.SAVE -> {
                    CbtBar(
                        title,
                        isSave = true
                    ) {
                        //commit
                        viewModel.saveLog()
                        Toast.makeText(context, "Log saved", Toast.LENGTH_LONG).show()
                        viewModel.clearLog()
                        navController.navigate(Screen.MainMenu.route)
                    }

                }
                MenuAction.CLOSE -> {
                    CbtBar(title) {
                        closeBehavior.invoke()
                    }
                }
            }

        },
        bottomBar = {
            destination?.let {
                NavigationButtons(
                    instructions = instructions,
                    nextButton = nextButton,
                    backButton = backButton
                )
            }
        },
    ) {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp, 0.dp, 12.dp, 0.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Column(
                    Modifier
                        .wrapContentSize()
                        .padding(bottom = 50.dp)
                ) {
                    body.invoke()
                }
            }
        }
    }
}

@Composable
fun NavigationButtons(
    instructions: String?,
    backButton: @Composable () -> Unit,
    nextButton: @Composable () -> Unit
) {
    val context = LocalContext.current
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
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp)
            ) {
                backButton.invoke()
            }
            Row(modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp)
                .clickable {
                    instructions?.let { string: String ->

                        Toast
                            .makeText(context, string, Toast.LENGTH_LONG)
                            .show()
                    }

                }) {
                nextButton.invoke()
            }

        }
    }
}

enum class MenuAction {
    CLEAR,
    SAVE,
    CLOSE
}
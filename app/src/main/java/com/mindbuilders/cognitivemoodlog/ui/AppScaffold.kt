package com.mindbuilders.cognitivemoodlog.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AndroidUriHandler
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mindbuilders.cognitivemoodlog.data.privacyPolicy
import com.mindbuilders.cognitivemoodlog.view.LogViewModel
import com.mindbuilders.cognitivemoodlog.nav.Screen
import androidx.compose.ui.platform.UriHandler

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
    closeBehavior: () -> Unit = {},
    backButton: @Composable () -> Unit = {
        CbtButton(
            text = "Back", modifier = Modifier.fillMaxWidth()
        ) {
            navController.popBackStack()
        }
    },
    nextButton: @Composable () -> Unit = {
        CbtButton(
            text = "Next", modifier = Modifier.fillMaxWidth(),
            isEnabled = destEnabled
        ) {
            destination?.route?.let {
                navController.navigate(it)
            }
        }
    },
    body: @Composable () -> Unit
) {
    val context = LocalContext.current
    var aboutDialogIsShowing by rememberSaveable { mutableStateOf(false)}
    val abandonDialogIsShowing: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }

    if (aboutDialogIsShowing) {
        AlertDialog(onDismissRequest = { aboutDialogIsShowing = false },
            backgroundColor = MaterialTheme.colors.background,
            text = {
                Column {
                    Text("""
                        Thank you for using my app. This app helped me with my current career as an app developer and I hope it is helpful to you as you work with your own thoughts and emotions.
                       
            
                            """.trimIndent(), color = MaterialTheme.colors.onBackground)
                    ClickableText(text = privacyPolicy, onClick = {
                        AndroidUriHandler(context).openUri("https://sites.google.com/view/cognitivemoodlogprivacypolicy")
                    } )
                }
            },
            confirmButton = {
                CbtButton(text = "OK") {
                    aboutDialogIsShowing = false
                }
            })
    }

    val clearAction: () -> Unit = { abandonDialogIsShowing.value = true }
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
                    ) {
                        GimmeAction(
                            name = "clearButton",
                            vector = Icons.Default.Clear,
                            actionAction = clearAction
                        )
                    }
                }
                MenuAction.SAVE -> {
                    CbtBar(
                        title,
                    ) {
                        GimmeAction(
                            name = "clearButton",
                            vector = Icons.Default.Clear,
                            actionAction = clearAction
                        )
                        GimmeAction(
                            name = "saveButton",
                            vector = Icons.Default.Done
                        )
                        { //commit
                            viewModel.saveLog()
                            Toast.makeText(context, "Log saved", Toast.LENGTH_LONG).show()
                            viewModel.clearLog()
                            navController.navigate(Screen.MainMenu.route)
                        }

                    }


                }
                MenuAction.CLOSE -> {
                    CbtBar(title) {
                        GimmeAction(name = "closeButton", vector = Icons.Default.Close) {
                            closeBehavior.invoke()
                        }
                    }
                }
                MenuAction.NONE -> {
                    CbtBar(title)
                }
                MenuAction.ABOUT -> {
                    CbtBar(title) {
                        GimmeAction(name = "about", vector = Icons.Default.Info ) {
                            aboutDialogIsShowing = true
                        }
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
    CLOSE,
    NONE,
    ABOUT
}
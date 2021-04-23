package com.mindbuilders.cognitivemoodlog.ui

import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.mindbuilders.cognitivemoodlog.nav.Screen
import com.mindbuilders.cognitivemoodlog.view.LogViewModel

@Composable
fun AbandonDialog(
    navController: NavController,
    viewModel: LogViewModel,
    isShowing: MutableState<Boolean>
) {
    val dismiss = { isShowing.value = false }
    if (isShowing.value) {
        AlertDialog(
            onDismissRequest = dismiss,
            backgroundColor = MaterialTheme.colors.background,
            text = {

                    ScrollableText(
                        "Are you sure you want to cancel this log?  All data will be deleted.",
                        modifier = Modifier.height(300.dp)
                    )

            },
            dismissButton = {
                CbtButton(text = "Discard Log", onClick = {
                    dismiss.invoke()
                    viewModel.clearLog()
                    navController.navigate(Screen.MainMenu.route)
                })
            },
            confirmButton = {
                CbtButton(text = "Continue Log", onClick = dismiss)
            })
    }

}
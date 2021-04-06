package com.mindbuilders.cognitivemoodlog.view.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.mindbuilders.cognitivemoodlog.view.LogViewModel
import com.mindbuilders.cognitivemoodlog.view.Screen

@Composable
fun AbandonDialog(navController: NavController, viewModel: LogViewModel, isShowing: MutableState<Boolean>) {
    val dismiss = { isShowing.value = false }
    if (isShowing.value) {
        AlertDialog(onDismissRequest = dismiss,
            text = { ScrollableText("Are you sure you want to cancel this log?  All data will be deleted.", modifier = Modifier.height(300.dp)) },
            dismissButton = {
                Button(onClick = {
                    dismiss.invoke()
                    viewModel.clearLog()
                    navController.navigate(Screen.DescribeSituation.route)
                }) {
                    Text("Discard Log")
                }
            },
            confirmButton = {
                Button(onClick = dismiss) {
                    Text("Continue Log")
                }
            })
    }

}
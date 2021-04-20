package com.mindbuilders.cognitivemoodlog.view

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun EnterPassword(context: Context, viewModel: LogViewModel) {

    var areYouSure by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }

    val dismiss = { viewModel.exitPasswordState() }
    AlertDialog(onDismissRequest =
    dismiss,
        dismissButton = {
            Button(onClick = {
                areYouSure = true
            }) {
                Text("Ignore Old Data")
            }
        },
        confirmButton = {
            Button(onClick = {
                viewModel.setPassword(password)
            }) {
                Text("OK")
            }
        }, title = { Text("Enter Your Password To Unlock Your Data.") },
        text = {
            Column {
                Text("If you want to start fresh and don't want your old logs, click the \"Ignore Old Data\" button.")
                OutlinedTextField(
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp),
                    value = password,
                    visualTransformation = PasswordVisualTransformation(),
                    label = { Text("enter password") },
                    onValueChange = {
                        password = it
                    })
            }
        })


    if (areYouSure) {
        AlertDialog(
            onDismissRequest = {
                areYouSure = false
            },
            dismissButton = {
                Button(onClick = {
                    areYouSure = false
                }) {
                    Text("Cancel")
                }
            },
            confirmButton = {
                Button(onClick = {
                    areYouSure = false
                    dismiss.invoke()
                    viewModel.stopMigration(context = context)
                }) {
                    Text("Ignore Old Logs")
                }
            }, text = {
                Text(
                    """
                    Are you sure you want ignore your old logs? 
                    You won't be able to see them in your previous logs.
                """.trimIndent()
                )
            }
        )
    }
}
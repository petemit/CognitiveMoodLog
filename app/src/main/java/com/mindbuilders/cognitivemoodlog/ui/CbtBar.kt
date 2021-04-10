package com.mindbuilders.cognitivemoodlog.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


@Composable
fun CbtBar(title: String = "", isSave: Boolean = false, actionAction: () -> Unit = {}) {
    TopAppBar(
        title = { Text(title) },
        backgroundColor = MaterialTheme.colors.primarySurface,
        navigationIcon = {
            Icon(
                Icons.Default.Menu,
                "menuButton",
                modifier = Modifier.clickable {
                    // scaffoldState.drawerState.open()
                }
            )
        },
        actions = {
            gimmeAction(
                name = "clearButton",
                vector = Icons.Default.Clear,
                actionAction = actionAction
            )
            if (isSave) {
                gimmeAction(
                    name = "saveButton",
                    vector = Icons.Default.Done,
                    actionAction = actionAction
                )
            }
        })
}

@Composable
private fun gimmeAction(name: String, vector: ImageVector, actionAction: () -> Unit) {
    Icon(
        imageVector = vector,
        name,
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .clickable {
                actionAction.invoke()
            }
    )
}
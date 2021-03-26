package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun CbtBar() {
    TopAppBar(title = { Text("Top AppBar") },
        backgroundColor = MaterialTheme.colors.primarySurface,
        navigationIcon = {
            Icon(
                Icons.Default.Menu,
                "menuButton",
                modifier = Modifier.clickable {
                    // scaffoldState.drawerState.open()
                }
            )
        })
}
package com.mindbuilders.cognitivemoodlog.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp


@Composable
fun CbtBar(
    title: String = "",
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = { Text(title, color = MaterialTheme.colors.onPrimary) },
        backgroundColor = MaterialTheme.colors.primarySurface,
        //todo I don't love how this turned out. I  should hoist this behavior
        actions = actions)
}

@Composable
fun GimmeAction(name: String, vector: ImageVector, actionAction: () -> Unit) {
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
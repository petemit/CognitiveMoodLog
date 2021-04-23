package com.mindbuilders.cognitivemoodlog.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Header(string: String) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        color = MaterialTheme.colors.surface,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.primarySurface)
    ) {
        Text(
            if (string.isEmpty()) {
                ""
            } else {
                string
            }, modifier = Modifier.padding(8.dp)
        )
    }

}
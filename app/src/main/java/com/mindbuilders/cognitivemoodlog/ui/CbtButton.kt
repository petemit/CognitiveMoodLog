package com.mindbuilders.cognitivemoodlog.ui

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CbtButton(
    text: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    val defaults = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.surface)
    Button(modifier = modifier,colors = defaults , onClick = onClick, enabled = isEnabled) {
        Text(text = text, style = MaterialTheme.typography.button)
    }
}
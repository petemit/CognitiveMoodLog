package com.mindbuilders.cognitivemoodlog.ui

import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CbtButton(
    text: String,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(modifier = modifier, onClick = onClick, enabled = isEnabled) {
        Text(text = text, style = MaterialTheme.typography.button)
    }
}
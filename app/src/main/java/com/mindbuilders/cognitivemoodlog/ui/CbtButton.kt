package com.mindbuilders.cognitivemoodlog.ui

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
//todo lol... I think this button officially didn't buy me anything
fun CbtButton(name: String, modifier: Modifier, isEnabled: Boolean = true, action: () -> Unit) {
    Button(modifier = modifier, onClick = action, enabled = isEnabled) {
        Text(text = name, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold)
    }
}
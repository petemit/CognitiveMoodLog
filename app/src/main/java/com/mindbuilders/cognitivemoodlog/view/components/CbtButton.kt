package com.mindbuilders.cognitivemoodlog.view.components

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CbtButton(name: String, modifier: Modifier, navigate: () -> Unit) {
    Button(modifier = modifier, onClick = navigate) {
        Text(text = name)
    }
}
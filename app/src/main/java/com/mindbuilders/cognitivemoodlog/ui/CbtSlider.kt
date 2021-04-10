package com.mindbuilders.cognitivemoodlog.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.mindbuilders.cognitivemoodlog.util.roundTo

@Composable
fun CbtSlider(value: Float, enabled: Boolean = true, onValueChanged: (Float) -> Unit) {
    Slider(
        value = value,
        steps = 10,
        enabled = enabled,
        valueRange = 0f..10f,
        onValueChange = onValueChanged
    )
    Text(value.roundTo(0).toString())
}

@Composable
fun inertSlider(value: Float) {
    Column {
        Slider(
            value = value,
            steps = 10,
            valueRange = 0f..10f,
            onValueChange = {},
            enabled = false
        )
        Text(value.roundTo(0).toString())
    }
}
package com.mindbuilders.cognitivemoodlog.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.mindbuilders.cognitivemoodlog.util.roundTo

@Composable
fun CbtSlider(value: Float, enabled: Boolean = true, onValueChanged: (Float) -> Unit) {
    Column {
        Slider(
            value = value,
            steps = 10,
            enabled = enabled,
            valueRange = 0f..10f,
            onValueChange = onValueChanged
        )
        Text(
            value.roundTo(0).toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()

        )

    }
}

@Composable
fun InertSlider(value: Float) {
    CbtSlider(value = value, enabled = false, onValueChanged = {})
}
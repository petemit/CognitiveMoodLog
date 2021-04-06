package com.mindbuilders.cognitivemoodlog.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindbuilders.cognitivemoodlog.ui.theme.CognitiveMoodLogTheme

@Composable
fun ScrollableSituation(situation: String) {
    ScrollableText(
        string = "Situation: $situation",
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 8.dp)
    )
}

@Composable
fun ScrollableText(string: String, modifier: Modifier) {
    BasicTextField(
        value = string,
        onValueChange = {},
        readOnly = true,
        modifier = modifier
    )
}

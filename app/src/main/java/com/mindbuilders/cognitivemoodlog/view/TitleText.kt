package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.mindbuilders.cognitivemoodlog.ui.theme.CognitiveMoodLogTheme

@Composable
fun TitleText(text: String = "") {
    Text(text = text, fontSize = 30.sp)
}

@Preview(showBackground = true)
@Composable
fun TitleText_Preview() {
    CognitiveMoodLogTheme {
        Column {
            TitleText("Hello There")
            Text("Hello There")
        }
    }
}
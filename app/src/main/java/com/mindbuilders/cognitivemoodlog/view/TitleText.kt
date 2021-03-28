package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindbuilders.cognitivemoodlog.ui.theme.CognitiveMoodLogTheme

@Composable
fun TitleText(text: String = "") {
    Text(text = text, fontSize = 20.sp, modifier = Modifier.padding(12.dp))
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
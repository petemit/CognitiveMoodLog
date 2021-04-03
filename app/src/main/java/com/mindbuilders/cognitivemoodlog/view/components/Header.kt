package com.mindbuilders.cognitivemoodlog.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun Header(string: String) {
    Text(string, modifier = Modifier
        .background(Color.LightGray)
        .padding(12.dp, 4.dp, 12.dp, 4.dp)
        .fillMaxWidth()
    )
}
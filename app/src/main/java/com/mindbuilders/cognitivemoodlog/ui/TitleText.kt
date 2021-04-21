package com.mindbuilders.cognitivemoodlog.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleText(text: String = "") {
    Text(text = text, style = MaterialTheme.typography.h1, modifier = Modifier.padding(vertical = 12.dp))
}
package com.mindbuilders.cognitivemoodlog.view.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleText(text: String = "") {
    Text(text = text, fontSize = 20.sp, modifier = Modifier.padding(vertical = 12.dp))
}
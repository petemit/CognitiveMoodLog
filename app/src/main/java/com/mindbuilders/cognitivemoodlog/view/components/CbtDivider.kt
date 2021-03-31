package com.mindbuilders.cognitivemoodlog.view.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CbtDivider() {
    Divider(thickness = 1.dp, modifier = Modifier.padding(top = 4.dp, bottom = 4.dp))
}
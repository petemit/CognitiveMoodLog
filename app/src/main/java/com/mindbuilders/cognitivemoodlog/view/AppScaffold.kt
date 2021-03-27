package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppScaffold(title: String, body: @Composable () -> Unit) {
    Scaffold(
        topBar = { CbtBar(title) },
    ) {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize().padding(6.dp,0.dp,6.dp,0.dp)
        ) { body.invoke() }
    }
}
package com.mindbuilders.cognitivemoodlog.view.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindbuilders.cognitivemoodlog.ui.theme.CognitiveMoodLogTheme

@Composable
fun SituationText(situation: String, situationDialog: MutableState<Boolean>) {
    val dismiss = { situationDialog.value = false}
    if (situationDialog.value) {
        AlertDialog(
            onDismissRequest = dismiss,
            text = { ScrollableText(situation, modifier = Modifier.height(200.dp)) },
            confirmButton = {
                Button(onClick = dismiss) {
                    Text("Close")
                }
            })
    }
    Text(
        text = "Situation: $situation",
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                situationDialog.value = true
            }
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

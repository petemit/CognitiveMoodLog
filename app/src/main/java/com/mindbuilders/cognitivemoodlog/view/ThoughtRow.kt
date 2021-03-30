package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import com.mindbuilders.cognitivemoodlog.model.Thought
import com.mindbuilders.cognitivemoodlog.util.roundTo
import com.mindbuilders.cognitivemoodlog.view.components.ScrollableText

@Composable
fun ThoughtRow(thought: Thought, isBefore: Boolean) {
    val starting: Float
    val thoughtString: String
    if (isBefore) {
        starting = thought.beliefBefore
        thoughtString = thought.thoughtBefore
    } else {
        starting = thought.beliefAfter
        thoughtString = thought.thoughtAfter
    }

    var belief by remember { mutableStateOf(starting) }
    var openDialog = remember { mutableStateOf(false)  }

    val dismiss = { openDialog.value = false}
    if (openDialog.value) {
        AlertDialog(onDismissRequest = dismiss, text = {ScrollableText(thoughtString, modifier = Modifier.height(300.dp))}, confirmButton = {
            Button(onClick = dismiss) {
                Text("Close")
            }
        })
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            thoughtString,
            modifier = Modifier
                .clickable {
                    openDialog.value = true
                }
                .padding(12.dp)
                .weight(1f)
                .height(60.dp),
            overflow = TextOverflow.Ellipsis,
            maxLines = 3,
        )
        Column(
            modifier = Modifier
                .width(200.dp)
                .weight(1f)
        ) {
            Slider(
                value = belief,
                steps = 10,
                valueRange = 0f..10f,
                onValueChange = {
                    belief = it
                    //todo this smells funky to manage state this way, not sure what to do yet
                    if (isBefore) {
                        thought.beliefBefore =
                            it.roundTo(0)
                    } else {
                        thought.beliefAfter =
                            it.roundTo(0)
                    }
                })
            Text(belief.roundTo(0).toString())
        }
    }
}
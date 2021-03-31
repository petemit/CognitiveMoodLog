package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mindbuilders.cognitivemoodlog.model.Thought
import com.mindbuilders.cognitivemoodlog.util.roundTo
import com.mindbuilders.cognitivemoodlog.view.components.CbtDivider
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
        thoughtString = "Negative Thought: ${thought.thoughtBefore}"
    }

    var belief by remember { mutableStateOf(starting) }
    var positiveThought by remember { mutableStateOf("") }
    val openDialog = remember { mutableStateOf(false) }

    val dismiss = { openDialog.value = false }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = dismiss,
            text = { ScrollableText(thoughtString, modifier = Modifier.height(300.dp)) },
            confirmButton = {
                Button(onClick = dismiss) {
                    Text("Close")
                }
            })
    }
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    thoughtString,
                    modifier = Modifier
                        .clickable {
                            openDialog.value = true
                        }
                        .padding(12.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3,
                )
                if (!isBefore) {

                    OutlinedTextField(
                        modifier = Modifier
                            .padding(horizontal = 12.dp),
                        value = positiveThought,
                        label = { Text("Positive Thought") },
                        onValueChange = {
                            positiveThought = it
                            thought.thoughtAfter = it
                        })
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
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
        }//end row
        CbtDivider()
    }//end column
}

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
import com.mindbuilders.cognitivemoodlog.view.components.CbtSlider
import com.mindbuilders.cognitivemoodlog.view.components.ScrollableText
import com.mindbuilders.cognitivemoodlog.view.components.inertSlider

@Composable
fun ThoughtRow(
    thought: Thought,
    isBefore: Boolean = true,
    isReview: Boolean = false,
    viewModel: LogViewModel
) {


    var posBelief by remember { mutableStateOf(thought.posBelief) }
    var beforeBelief by remember { mutableStateOf(thought.negBeliefBefore) }
    var positiveThought by remember { mutableStateOf(thought.thoughtAfter) }
    var selectedThought by remember { mutableStateOf("") }
    val openDialog = remember { mutableStateOf(false) }

    val dismiss = { openDialog.value = false }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = dismiss,
            text = { ScrollableText(selectedThought, modifier = Modifier.height(300.dp)) },
            confirmButton = {
                Button(onClick = dismiss) {
                    Text("Close")
                }
            })
    }
    Column {
        //Negative Row
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                RowThought(thought.thoughtBefore) {
                    selectedThought = thought.thoughtBefore
                    openDialog.value = true
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {

                if (isBefore && !isReview) {
                    CbtSlider(value = beforeBelief)
                    {
                        beforeBelief = it
                        viewModel.editThought {
                            thought.negBeliefBefore = it.roundTo(0)
                        }

                    }
                } else if (!isBefore && !isReview) {
                    inertSlider(value = beforeBelief)
                } else if (isReview) {
                    Text("$beforeBelief -> ${thought.negBeliefAfter}")
                }
            }
        }
        //Positive Row
        if (!isBefore || isReview) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    if (isReview) {
                        RowThought(thought.thoughtAfter) {
                            selectedThought = thought.thoughtAfter
                            openDialog.value = true
                        }
                    } else {
                        OutlinedTextField(
                            modifier = Modifier
                                .padding(horizontal = 12.dp),
                            value = positiveThought,
                            label = { Text("Positive Thought") },
                            onValueChange = {
                                positiveThought = it
                                viewModel.editThought {
                                    thought.thoughtAfter = it
                                }
                            })
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    if (!isReview) {
                        CbtSlider(value = posBelief)
                        {
                            posBelief = it
                            viewModel.editThought {
                                thought.posBelief = it.roundTo(0)
                            }

                        }
                    } else {
                        inertSlider(value = posBelief)
                    }
                }
            }
        }//end row
        CbtDivider()
    }//end column
}

@Composable
private fun RowThought(thoughtString: String, func: () -> Unit) {
    Text(
        thoughtString,
        modifier = Modifier
            .clickable { func.invoke() }
            .padding(12.dp),
        overflow = TextOverflow.Ellipsis,
        maxLines = 3,
    )
}

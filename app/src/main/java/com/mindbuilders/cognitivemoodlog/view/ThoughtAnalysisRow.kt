package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindbuilders.cognitivemoodlog.R
import com.mindbuilders.cognitivemoodlog.model.CognitiveDistortion
import com.mindbuilders.cognitivemoodlog.model.Thought
import com.mindbuilders.cognitivemoodlog.ui.CbtDivider
import com.mindbuilders.cognitivemoodlog.ui.ScrollableText

@Composable
fun ThoughtAnalysisRow(
    thought: Thought,
    viewModel: LogViewModel,
    modifier: Modifier = Modifier.padding(12.dp)
) {
    val cognitiveDistortions: List<CognitiveDistortion> by
    viewModel.cognitiveDistortionList.observeAsState(listOf())
    var selectedCd: Int by rememberSaveable {
        mutableStateOf(
            viewModel.cognitiveDistortionList.value?.indexOf(
                thought.cognitiveDistortion
            ) ?: -1
        )
    }
    var infoCd: CognitiveDistortion? by rememberSaveable {
        mutableStateOf(null)
    }
    var showDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var isOpen: Boolean by remember { mutableStateOf(false) }
    val cd = cognitiveDistortions.getOrNull(selectedCd)
    val dismiss = {
        showDialog = false
        infoCd = null
    }
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

        if (showDialog) {
            AlertDialog(
                onDismissRequest = dismiss,
                text = {
                    infoCd?.let {
                        val text = if (it.description.isNotEmpty()) {
                            it.description
                        } else {
                            it.summary
                        }
                        ScrollableText(
                            string = text,
                            modifier = Modifier.height(200.dp)
                        )
                    }
                },
                confirmButton = {
                    Button(onClick = dismiss) {
                        Text("Close")
                    }
                })
        }
        Text(
            "Negative Thought: ${thought.thoughtBefore}",
            modifier = Modifier
                .padding(bottom = 12.dp),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .background(Color.LightGray)
                .border(width = 1.dp, color = MaterialTheme.colors.onBackground)
                .padding(8.dp)
                .clickable { isOpen = true }
        ) {
            Row {
                Row(modifier = Modifier.weight(3f), horizontalArrangement = Arrangement.Start) {
                    if (selectedCd == -1) {
                        Text("Select a Cognitive Distortion")
                    } else {
                        Text(cd?.name ?: "")

                    }
                }
                Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.End) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_drop_down_circle_24),
                        contentDescription = "expand dropdown",
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
            DropdownMenu(expanded = isOpen, onDismissRequest = { isOpen = false }) {
                cognitiveDistortions.forEachIndexed { index, cd ->
                    DropdownMenuItem(onClick = {
                        selectedCd = index
                        viewModel.editThought {
                            thought.cognitiveDistortion = cd
                        }
                        isOpen = false
                    }) {
                        Row {
                            //todo find a way to make the text not displace its siblings
                            Text(cd.name, maxLines = 2)
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_info_24),
                                contentDescription = "more information about this cognitive distortion",
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .requiredSize(24.dp)
                                    .clickable {
                                        infoCd = cd
                                        showDialog = true
                                    }
                            )
                        }
                    }
                }
            }
        }
        Text(
            cd?.summary ?: "",
            modifier = Modifier
                .padding(vertical = 12.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        CbtDivider()
    }
}
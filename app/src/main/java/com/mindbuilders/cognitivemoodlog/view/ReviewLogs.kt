package com.mindbuilders.cognitivemoodlog.view

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mindbuilders.cognitivemoodlog.model.*
import com.mindbuilders.cognitivemoodlog.view.components.AppScaffold
import com.mindbuilders.cognitivemoodlog.view.components.CbtDivider
import com.mindbuilders.cognitivemoodlog.view.components.MenuAction
import com.mindbuilders.cognitivemoodlog.view.components.TitleText
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ReviewLogs(navController: NavHostController, viewModel: LogViewModel) {
    var selectedLog: LogEntry? by rememberSaveable { mutableStateOf(null) }
    var situationDialog = remember { mutableStateOf(false) }
    viewModel.refreshLogEntries()
    val realmLogEntries: List<RealmLogEntry>? =
        viewModel.realmLogEntries.observeAsState(listOf()).value
    /*  todo I haven't dug into how to better interop with RealmObjects...
     *      They seem to be kind of a pain... for now I'll just map them.
     */
    val logEntries = realmLogEntries?.mapNotNull { logEntry ->
        LogEntry(
            logEntry.id.toHexString(),
            emotions = logEntry.emotions.map { emotion ->
                Emotion(
                    id = emotion.id.hashCode(),
                    category = emotion.category,
                    name = emotion.name,
                    emotion.strengthBefore,
                    emotion.strengthAfter
                )
            },
            thoughts = logEntry.thoughts.map { thought ->
                Thought(
                    id = thought.id.toHexString(),
                    thoughtBefore = thought.thoughtBefore,
                    thoughtAfter = thought.thoughtAfter,
                    negBeliefBefore = thought.negBeliefBefore,
                    negBeliefAfter = thought.negBeliefAfter,
                    posBelief = thought.posBelief,
                    cognitiveDistortion = CognitiveDistortion(
                        id = thought.cognitiveDistortion?.id.hashCode(),
                        name = thought.cognitiveDistortion?.name ?: "",
                        summary = thought.cognitiveDistortion?.summary ?: "",
                        description = thought.cognitiveDistortion?.description ?: "",
                        example = thought.cognitiveDistortion?.example ?: ""
                    )
                )
            },
            situation = logEntry.situation,
            date = logEntry.date
        )
    }

    AppScaffold(
        title = "See Past Logs",
        navController = navController,
        viewModel = viewModel,
        nextButton = {},
        backButton = {},
        barActionBehavior = MenuAction.CLOSE,
        closeBehavior = {
            if (selectedLog != null) {
                selectedLog = null
            } else {
                navController.popBackStack()
            }
        }

    ) {
        if (selectedLog != null) {
            ShowEmotionAndThoughtReview(
                "Log from ${selectedLog?.date}",
                situation = selectedLog?.situation ?: "",
                selectedLog?.emotions,
                selectedLog?.thoughts ?: listOf(),
                viewModel = viewModel,
                situationDialog = situationDialog
            )
        } else {

            LazyColumn {
                item {
                    TitleText("Tap On a Row To Review Your Past Logs")
                    CbtDivider()
                }
                logEntries?.let { logEntries ->
                    items(logEntries) { logEntry ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .clickable {
                                    selectedLog = logEntry
                                }

                        ) {
                            Text(
                                logEntry.situation,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 3,
                                modifier = Modifier.weight(1f)
                            )
                            Text(logEntry.date.toFormattedDate(), modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

private fun Date.toFormattedDate(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd h:mm a", Locale.getDefault())
    return dateFormat.format(this)
}


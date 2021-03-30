package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.mindbuilders.cognitivemoodlog.model.Thought

@Composable
fun ThoughtList(viewModel: LogViewModel, isBefore: Boolean) {
    val list: List<Thought> = viewModel.thoughts.observeAsState(listOf()).value
    LazyColumn {
        items(list, key = { item: Thought -> item.id }) { thought ->
            ThoughtRow(thought, isBefore)
        }
    }
}
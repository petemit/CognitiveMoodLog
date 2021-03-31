package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.foundation.lazy.items

import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindbuilders.cognitivemoodlog.model.Emotion

@ExperimentalFoundationApi
@Composable
fun EmotionList(viewModel: LogViewModel, isBefore: Boolean) {
    val list: List<Emotion>? = viewModel.emotionList.observeAsState().value
    val groupedEmotions = viewModel.groupedEmotions.observeAsState().value
    list?.let {
        LazyColumn {
            groupedEmotions?.forEach { (category, emotions) ->
                stickyHeader {
                    Header(category)
                }
                items(emotions, key = { item: Emotion -> item.id }) { emotion ->
                    EmotionRow(emotion, isBefore)
                }
            }
        }
    }
}

@Composable
fun Header(string: String) {
    Text(string, modifier = Modifier
        .background(androidx.compose.ui.graphics.Color.LightGray)
        .padding(12.dp, 4.dp, 12.dp, 4.dp)
        .fillMaxWidth()
    )
}
package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindbuilders.cognitivemoodlog.model.Emotion
import com.mindbuilders.cognitivemoodlog.util.roundTo
import com.mindbuilders.cognitivemoodlog.view.components.CbtDivider

@Composable
fun AfterEmotionRow(emotion: Emotion) {
    var afterEmotionStrength by remember { mutableStateOf(emotion.strengthAfter) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Row (horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(
                emotion.name,
                modifier = Modifier
                    .padding(bottom = 12.dp),
                fontSize = 17.sp,
                textAlign = TextAlign.Center
            )
        }
        Row {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Before:",
                    modifier = Modifier
                        .padding(bottom = 12.dp),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
                Slider(
                    value = emotion.strengthBefore,
                    steps = 10,
                    valueRange = 0f..10f,
                    onValueChange = {},
                    enabled = false
                )
                Text(emotion.strengthBefore.roundTo(0).toString())
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "After:",
                    modifier = Modifier
                        .padding(bottom = 12.dp),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
                Slider(
                    value = afterEmotionStrength,
                    steps = 10,
                    valueRange = 0f..10f,
                    onValueChange = {
                        afterEmotionStrength = it.roundTo(0)
                        emotion.strengthAfter = it.roundTo(0)
                    },
                )
                Text(afterEmotionStrength.roundTo(0).toString())
            }
        }
        CbtDivider()
    }
}
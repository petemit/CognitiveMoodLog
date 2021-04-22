package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mindbuilders.cognitivemoodlog.model.Emotion
import com.mindbuilders.cognitivemoodlog.ui.CbtSlider
import com.mindbuilders.cognitivemoodlog.util.roundTo

@Composable
fun EmotionRow(emotion: Emotion, isBefore: Boolean, viewModel: LogViewModel) {
    val starting: Float = if (isBefore) {
        emotion.strengthBefore
    } else {
        emotion.strengthAfter
    }
    var strength by remember { mutableStateOf(starting) }
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(emotion.name, modifier = Modifier.padding(12.dp))
        Spacer(modifier = Modifier.weight(1f))
        Column(modifier = Modifier.width(200.dp)) {
            CbtSlider(
                value = strength,
                onValueChanged = {
                    strength = it
                    viewModel.editEmotion {
                        emotion.strengthBefore =
                            strength.roundTo(0)
                    }
                }
            )
        }
    }
}



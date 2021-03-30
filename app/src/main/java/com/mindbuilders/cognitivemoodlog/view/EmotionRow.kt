package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mindbuilders.cognitivemoodlog.model.Emotion
import com.mindbuilders.cognitivemoodlog.model.emotions
import com.mindbuilders.cognitivemoodlog.ui.theme.CognitiveMoodLogTheme
import com.mindbuilders.cognitivemoodlog.util.roundTo
import java.lang.StringBuilder
import java.math.RoundingMode
import java.text.DecimalFormat

@Composable
fun EmotionRow(emotion: Emotion, isBefore: Boolean) {
    val starting: Float = if (isBefore) {
        emotion.strengthBefore
    } else {
        emotion.strengthAfter
    }
    var strength by remember { mutableStateOf(starting)}
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(emotion.name, modifier = Modifier.padding(12.dp))
        Spacer(modifier = Modifier.weight(1f))
        Column(modifier = Modifier.width(200.dp)) {
            Slider(
                value = strength,
                steps = 10,
                valueRange = 0f..10f,
                onValueChange = {
                strength = it
                    //todo this smells funky to manage state this way, not sure what to do yet
                    if (isBefore) {
                        emotion.strengthBefore =
                            it.roundTo(0)
                    } else  {
                        emotion.strengthAfter =
                            it.roundTo(0)
                    }
            })
            Text(strength.roundTo(0).toString())
        }
    }
}

@Preview
@Composable
fun EmotionRow_Preview() {
    CognitiveMoodLogTheme {
        EmotionRow(emotions[3], true)
    }
}

package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindbuilders.cognitivemoodlog.util.roundTo
import com.mindbuilders.cognitivemoodlog.view.components.CbtDivider
import com.mindbuilders.cognitivemoodlog.view.components.CbtSlider
import com.mindbuilders.cognitivemoodlog.view.components.inertSlider


@Composable
fun AfterAnalysisRow(before: Float, after: Float, text: String, isReview: Boolean, onValueChanged: (Float) -> Unit) {
    var afterStrength by remember { mutableStateOf(after) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(
                text,
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
                inertSlider(value = before)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "After:",
                    modifier = Modifier
                        .padding(bottom = 12.dp),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
                if (!isReview) {
                    CbtSlider(value = afterStrength) {
                        afterStrength = it.roundTo(0)
                        onValueChanged.invoke(afterStrength.roundTo(0))
                    }
                } else {
                    inertSlider(value = afterStrength)
                }
            }
        }
        CbtDivider()
    }
}

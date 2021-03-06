package com.mindbuilders.cognitivemoodlog.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindbuilders.cognitivemoodlog.util.roundTo
import com.mindbuilders.cognitivemoodlog.ui.CbtDivider
import com.mindbuilders.cognitivemoodlog.ui.CbtSlider
import com.mindbuilders.cognitivemoodlog.ui.InertSlider


@Composable
fun AfterAnalysisRow(
    before: Float,
    after: Float,
    text: String,
    isReview: Boolean,
    onValueChanged: (Float) -> Unit
) {
    var afterStrength by remember { mutableStateOf(after) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Surface(
                modifier = Modifier.padding(vertical = 4.dp),
                color = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.onPrimary
            ) {
                Text(
                    text,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.h3,
                    textAlign = TextAlign.Center
                )
            }
        }
        Row {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Before:",
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .fillMaxWidth(),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
                InertSlider(value = before)
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "After:",
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                        .fillMaxWidth(),
                    fontSize = 15.sp,
                    textAlign = TextAlign.Center
                )
                if (!isReview) {
                    CbtSlider(value = afterStrength) {
                        afterStrength = it
                        onValueChanged.invoke(afterStrength.roundTo(0))
                    }
                } else {
                    InertSlider(value = afterStrength)
                }
            }
        }
        CbtDivider()
    }
}


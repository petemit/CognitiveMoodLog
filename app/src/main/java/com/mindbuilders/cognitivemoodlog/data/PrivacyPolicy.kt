package com.mindbuilders.cognitivemoodlog.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

val privacyPolicy: AnnotatedString = buildAnnotatedString {
    val string = "Privacy Policy"
    append(string)
    addStyle(
        style = SpanStyle(
            color = Color(0xFF3B88C5),
            fontSize = 16.sp,
            textDecoration = TextDecoration.Underline
        ),
        0, string.length
    )
}


package com.mindbuilders.cognitivemoodlog.model

data class SelectedEmotion(
    val id: String,
    val emotion: Emotion,
    var strengthBefore: Float,
    var strengthAfter: Float
)
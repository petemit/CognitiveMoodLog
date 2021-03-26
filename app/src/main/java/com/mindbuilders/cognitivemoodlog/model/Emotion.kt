package com.mindbuilders.cognitivemoodlog.model

data class Emotion(
    val id: Int,
    val category: String,
    val name: String,
    var strengthBefore: Int = 0,
    var strengthAfter: Int = 0
)
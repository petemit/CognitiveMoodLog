package com.mindbuilders.cognitivemoodlog.model

import java.util.*

data class Thought(
    val id : String = UUID.randomUUID().toString(),
    var thoughtBefore: String = "",
    var thoughtAfter: String = "",
    var beliefBefore: Float = 0f,
    var beliefAfter: Float = 0f,
    var cognitiveDistortion: CognitiveDistortion? = null
)

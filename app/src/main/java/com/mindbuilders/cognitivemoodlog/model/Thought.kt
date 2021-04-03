package com.mindbuilders.cognitivemoodlog.model

import java.util.*

data class Thought(
    val id : String = UUID.randomUUID().toString(),
    var thoughtBefore: String = "",
    var thoughtAfter: String = "",
    var negBeliefBefore: Float = 0f,
    var negBeliefAfter: Float = 0f,
    var posBelief: Float = 0f,
    var cognitiveDistortion: CognitiveDistortion? = null
)

package com.mindbuilders.cognitivemoodlog.model

data class Thought(
    val id : Int,
    var thoughtBefore: String,
    var thoughtAfter: String,
    var beliefBefore: Int = 0,
    var beliefAfter: Int = 0,
    var cognitiveDistortion: CognitiveDistortion?
)

package com.mindbuilders.cognitivemoodlog.model

data class Log(
    var situation: String,
    var emotions: List<Emotion>,
    var thoughts: List<Thought>
)
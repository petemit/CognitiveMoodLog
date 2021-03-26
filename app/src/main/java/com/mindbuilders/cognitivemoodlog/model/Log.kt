package com.mindbuilders.cognitivemoodlog.model

data class Log(
    var situation: String = "",
    var emotions: MutableList<Emotion> = mutableListOf(),
    var thoughts: MutableList<Thought> = mutableListOf()
)
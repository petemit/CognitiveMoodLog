package com.mindbuilders.cognitivemoodlog.model


import java.util.*

class LogEntry(var id: String,
               var situation: String,
               var emotions: List<Emotion>,
               var thoughts: List<Thought>,
               var date: Date)
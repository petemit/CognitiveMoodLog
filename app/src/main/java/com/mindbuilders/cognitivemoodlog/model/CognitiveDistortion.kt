package com.mindbuilders.cognitivemoodlog.model

data class CognitiveDistortion(
    val id: Int,
    val name: String,
    val summary: String,
    val description: String,
    val example: String
)

val cognitiveDistortions = listOf(
    CognitiveDistortion(
        id = 1,
        name = "Self-Blame",
        summary = "You blame yourself for problems outside of your control",
        description = "",
        example = ""
    ),
    CognitiveDistortion(
        id = 2,
        name = "Over-Generalization",
        summary = "You view things as a never-ending pattern of defeat",
        description = "",
        example = ""
    ),
    CognitiveDistortion(
        id = 3,
        name = "All-Or-Nothing",
        summary = "test1",
        description = "",
        example = ""
    ),
    CognitiveDistortion(id = 4, name = "Hello", summary = "test2", description = "", example = "")
)
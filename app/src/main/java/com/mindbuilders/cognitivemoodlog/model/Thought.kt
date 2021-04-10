package com.mindbuilders.cognitivemoodlog.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Thought(
    val id : String = UUID.randomUUID().toString(),
    var thoughtBefore: String = "",
    var thoughtAfter: String = "",
    var negBeliefBefore: Float = 0f,
    var negBeliefAfter: Float = 0f,
    var posBelief: Float = 0f,
    var cognitiveDistortion: CognitiveDistortion? = null
) : Parcelable

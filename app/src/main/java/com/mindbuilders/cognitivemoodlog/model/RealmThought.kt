package com.mindbuilders.cognitivemoodlog.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId

open class RealmThought(
    @PrimaryKey
    var id : ObjectId = ObjectId(),
    var thoughtBefore: String = "",
    var thoughtAfter: String = "",
    var negBeliefBefore: Float = 0f,
    var negBeliefAfter: Float = 0f,
    var posBelief: Float = 0f,
    var cognitiveDistortion: RealmCognitiveDistortion? = null
) : RealmObject()

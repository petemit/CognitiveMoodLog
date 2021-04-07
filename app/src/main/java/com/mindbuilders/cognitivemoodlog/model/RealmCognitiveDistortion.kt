package com.mindbuilders.cognitivemoodlog.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId


open class RealmCognitiveDistortion(
    @PrimaryKey
    var id: ObjectId = ObjectId(),
    var name: String = "",
    var summary: String = "",
    var description: String = "",
    var example: String = ""
): RealmObject()
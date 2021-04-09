package com.mindbuilders.cognitivemoodlog.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import java.util.*

open class RealmLogEntry(
    @PrimaryKey
    var id: ObjectId = ObjectId(),
    var situation: String = "",
    var emotions: RealmList<RealmEmotion> = RealmList(),
    var thoughts: RealmList<RealmThought> = RealmList(),
    var date: Date = Calendar.getInstance().time
): RealmObject()
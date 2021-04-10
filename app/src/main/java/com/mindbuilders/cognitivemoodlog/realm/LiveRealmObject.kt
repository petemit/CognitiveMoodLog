package com.mindbuilders.cognitivemoodlog.realm

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.RealmObjectChangeListener

/**
 * This code comes from: https://github.com/mongodb-university/realm-android-livedata/blob/final/app/src/main/java/com/mongodb/realm/livedataquickstart/model/LiveRealmObject.kt#L27
 */
class LiveRealmObject<T : RealmModel?> @MainThread constructor(obj: T?) : MutableLiveData<T>() {

    private val listener =
        RealmObjectChangeListener<T> { obj, objectChangeSet ->
            if (!objectChangeSet!!.isDeleted) {
                setValue(obj)
            } else { // Because invalidated objects are unsafe to set in LiveData, pass null instead.
                setValue(null)
            }
        }

    /**
     * Starts observing the RealmObject if we have observers and the object is still valid.
     */
    override fun onActive() {
        super.onActive()
        val obj = value
        if (obj != null && RealmObject.isValid(obj)) {
            RealmObject.addChangeListener(obj, listener)
        }
    }

    /**
     * Stops observing the RealmObject.
     */
    override fun onInactive() {
        super.onInactive()
        val obj = value
        if (obj != null && RealmObject.isValid(obj)) {
            RealmObject.removeChangeListener(obj, listener)
        }
    }

    var value: T? = obj
}
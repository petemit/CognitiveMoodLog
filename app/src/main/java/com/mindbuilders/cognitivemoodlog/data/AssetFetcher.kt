package com.mindbuilders.cognitivemoodlog.data

import com.mindbuilders.cognitivemoodlog.model.CognitiveDistortion
import com.mindbuilders.cognitivemoodlog.model.Emotion
import javax.inject.Inject

class AssetFetcher @Inject constructor() {
    fun getEmotions(): List<Emotion> {
        return listOf()
    }
    fun getCds(): List<CognitiveDistortion> {
        return listOf()
    }
}
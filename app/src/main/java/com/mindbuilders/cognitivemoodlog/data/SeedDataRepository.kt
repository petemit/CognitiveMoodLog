package com.mindbuilders.cognitivemoodlog.data

import com.mindbuilders.cognitivemoodlog.model.CognitiveDistortion
import com.mindbuilders.cognitivemoodlog.model.Emotion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SeedDataRepository @Inject constructor(private val assetFetcher: AssetFetcher) {

    suspend fun getEmotions(): List<Emotion> {
        return withContext(Dispatchers.IO) {
            return@withContext assetFetcher.getEmotions()
        }
    }

    suspend fun getCds(): List<CognitiveDistortion> {
        return withContext(Dispatchers.IO) {
            return@withContext assetFetcher.getCds()
        }
    }
}
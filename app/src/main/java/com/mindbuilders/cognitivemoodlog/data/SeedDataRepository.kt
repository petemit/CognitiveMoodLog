package com.mindbuilders.cognitivemoodlog.data

import android.util.Log
import com.mindbuilders.cognitivemoodlog.model.CognitiveDistortion
import com.mindbuilders.cognitivemoodlog.model.Emotion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SeedDataRepository @Inject constructor(private val assetFetcher: AssetFetcher) {

    suspend fun getEmotions(): List<Emotion> {

        return withContext(Dispatchers.IO) {
            try {
                return@withContext assetFetcher.getEmotions()
            } catch (e: Exception) { //being picky about exception types doesn't really do that much for me here.
                Log.e(SeedDataRepository::class.java.name, "couldn't read or parse emotion file")
                throw e
            }
        }

    }

    suspend fun getCds(): List<CognitiveDistortion> {
        return withContext(Dispatchers.IO) {
            try {
                return@withContext assetFetcher.getCds()
            } catch (e: Exception) { //being picky about exception types doesn't really do that much for me here.
                Log.e(SeedDataRepository::class.java.name, "couldn't read or parse emotion file")
                throw e
            }
        }
    }
}
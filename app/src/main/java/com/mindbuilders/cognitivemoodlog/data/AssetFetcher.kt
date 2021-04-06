package com.mindbuilders.cognitivemoodlog.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mindbuilders.cognitivemoodlog.model.CognitiveDistortion
import com.mindbuilders.cognitivemoodlog.model.Emotion
import javax.inject.Inject

class AssetFetcher @Inject constructor(private val context: Context) {
    fun getEmotions(): List<Emotion> {
        return getFileContent(filename = "emotions.json")
    }

    fun getCds(): List<CognitiveDistortion> {
        return getFileContent(filename = "cognitivedistortions.json")
    }

    private inline fun <reified T> getFileContent(filename: String): T {
        val text: String
        val emotionsJson = context.assets.open(filename).bufferedReader().use {
            //these files are small and so I'm not worried about performance, here.
            text = it.readText()
        }
//            val typeToken = (object: TypeToken<List<Emotion>>(){}).type
//            val result: List<Emotion> = Gson().fromJson(text, typeToken)

        return Gson().fromJson(text)

    }
}

inline fun <reified T> Gson.fromJson(json: String): T =
    fromJson(json, object : TypeToken<T>() {}.type)
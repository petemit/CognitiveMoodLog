package com.mindbuilders.cognitivemoodlog.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.mindbuilders.cognitivemoodlog.data.AssetFetcher
import com.mindbuilders.cognitivemoodlog.model.*
import javax.inject.Inject

class LogViewModel @Inject constructor(val assetFetcher: AssetFetcher): ViewModel() {
    private val _situation: MutableLiveData<String> = MutableLiveData()
    val situation: LiveData<String> = _situation

    //emotions
    private val _emotionList: MutableLiveData<List<Emotion>> = MutableLiveData(emotions)
    val emotionList: LiveData<List<Emotion>> = _emotionList


    val groupedEmotions = emotionList.map { emotionList -> emotionList.groupBy { it.category } }
    val selectedEmotions =
        emotionList.map { emotionList -> emotionList.filter { it.strengthBefore > 0 } }

    //thoughts
    private val _thoughts: MutableLiveData<MutableList<Thought>> = MutableLiveData(mutableListOf())
    val thoughts: LiveData<out List<Thought>> = _thoughts
    val hasPositiveThoughts: LiveData<Boolean> = thoughts.map { listOfThoughts ->
        listOfThoughts.all {
            it.thoughtAfter.isNotEmpty()
        }
    }
    val allCogged: LiveData<Boolean> = thoughts.map { listOfThoughts ->
        listOfThoughts.all {
            it.cognitiveDistortion != null
        }
    }

    //cognitive distortions
    val cognitiveDistortionList: LiveData<List<CognitiveDistortion>> =
        MutableLiveData(cognitiveDistortions)

    fun onSituationChange(newSituation: String) {
        _situation.value = newSituation
    }

    fun addBeforeThought(newThought: String) {
        val thought = Thought(thoughtBefore = newThought)
        _thoughts.value?.add(thought)
        _thoughts.notifyObserver()
    }

    fun editEmotion(func: () -> Unit) {
        func.invoke()
        _emotionList.notifyObserver()
    }

    fun editThought(func: () -> Unit) {
        func.invoke()
        _thoughts.notifyObserver()
    }
}

/**
 * Adding to a list doesn't cause an emission, so we gotta make one.
 */
fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}
package com.mindbuilders.cognitivemoodlog.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.mindbuilders.cognitivemoodlog.model.Emotion
import com.mindbuilders.cognitivemoodlog.model.Thought
import com.mindbuilders.cognitivemoodlog.model.emotions

class LogViewModel: ViewModel() {
    private val _situation: MutableLiveData<String> = MutableLiveData()
    val situation: LiveData<String> = _situation

    val emotionList: LiveData<List<Emotion>> = MutableLiveData(emotions)

    val groupedEmotions = emotionList.map { emotionList -> emotionList.groupBy { it.category } }

    private val _thoughts: MutableLiveData<MutableList<Thought>> = MutableLiveData(mutableListOf())
    val thoughts: LiveData<out List<Thought>> = _thoughts

    fun onSituationChange(newSituation: String) {
        _situation.value = newSituation
    }

    fun addBeforeThought(newThought: String) {
        val thought = Thought(thoughtBefore = newThought)
        _thoughts.value?.add(thought)
        _thoughts.notifyObserver()
    }
}

/**
 * Adding to a list doesn't cause an emission, so we gotta make one.
 */
fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}
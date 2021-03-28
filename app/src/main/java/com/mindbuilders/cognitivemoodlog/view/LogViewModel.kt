package com.mindbuilders.cognitivemoodlog.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.mindbuilders.cognitivemoodlog.model.Emotion
import com.mindbuilders.cognitivemoodlog.model.emotions

class LogViewModel: ViewModel() {
    private val _situation: MutableLiveData<String> = MutableLiveData()
    val situation: LiveData<String> = _situation

    val emotionList: LiveData<List<Emotion>> = MutableLiveData(emotions)

    val groupedEmotions = emotionList.map { emotionList -> emotionList.groupBy { it.category } }

    private val _thoughtBefore: MutableLiveData<String> = MutableLiveData()
    val thoughtBefore: LiveData<String> = _thoughtBefore

    fun onSituationChange(newSituation: String) {
        _situation.value = newSituation
    }

    fun onThoughtBeforeChange(newThought: String) {
        _thoughtBefore.value = newThought
    }
}
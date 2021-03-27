package com.mindbuilders.cognitivemoodlog.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mindbuilders.cognitivemoodlog.model.Emotion
import com.mindbuilders.cognitivemoodlog.model.emotions

class LogViewModel: ViewModel() {
    private val _situation: MutableLiveData<String> = MutableLiveData()
    val situation: LiveData<String> = _situation

    private val emotionList: LiveData<List<Emotion>> = MutableLiveData(emotions)

    fun onSituationChange(newSituation: String) {
        _situation.value = newSituation
    }
}
package com.mindbuilders.cognitivemoodlog.view

import androidx.lifecycle.*
import com.mindbuilders.cognitivemoodlog.data.SeedDataRepository

import com.mindbuilders.cognitivemoodlog.model.*
import com.mongodb.realm.livedataquickstart.model.LiveRealmResults
import dagger.Lazy
import io.realm.Realm
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class LogViewModel @Inject constructor(val repository: SeedDataRepository, val realm: Lazy<Realm>) :
    ViewModel() {

    //situation
    private val _situation: MutableLiveData<String> = MutableLiveData()
    val situation: LiveData<String> = _situation

    //emotions
    private val _emotionList: MutableLiveData<List<Emotion>> = MutableLiveData()
    val emotionList: LiveData<List<Emotion>> = _emotionList

    val groupedEmotions: LiveData<Map<String, List<Emotion>>> =
        emotionList.map { emotionList -> emotionList.groupBy { it.category } }
    val selectedEmotions: LiveData<List<Emotion>> =
        emotionList.map { emotionList -> emotionList.filter { it.strengthBefore > 0 } }

    //thoughts
    private val _thoughts: MutableLiveData<MutableList<Thought>> = MutableLiveData(mutableListOf())
    val thoughts: LiveData<out List<Thought>> = _thoughts

    val hasANegativeThought: LiveData<Boolean> = thoughts.map { listOfThoughts ->
        listOfThoughts.any {
            it.thoughtBefore.isNotEmpty()
        }
    }

    //whether all negative thoughts have a positive thought counterpart
    val hasPositiveThoughts: LiveData<Boolean> = thoughts.map { listOfThoughts ->
        listOfThoughts.all {
            it.thoughtAfter.isNotEmpty()
        }
    }

    //logentries
    suspend fun getLogEntries(): LiveRealmResults<LogEntry> {
        return repository.getLogs()
    }

    //loading status
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val isLoading: LiveData<Boolean> = _isLoading

    //cognitive distortions
    private val _cognitiveDistortionList: MutableLiveData<List<CognitiveDistortion>> =
        MutableLiveData()
    val cognitiveDistortionList: LiveData<List<CognitiveDistortion>> =
        _cognitiveDistortionList

    //Whether all thoughts have a cognitive distortion assigned
    val allCogged: LiveData<Boolean> = thoughts.map { listOfThoughts ->
        listOfThoughts.all {
            it.cognitiveDistortion != null
        }
    }

    init {
        viewModelScope.launch {
            _isLoading.value = true
            _emotionList.value = repository.getEmotions()
            _cognitiveDistortionList.value = repository.getCds()
            _isLoading.value = false
        }
    }

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

    fun clearLog() {
        _thoughts.value = mutableListOf()
        _emotionList.value = mutableListOf()
        _situation.value = ""
    }

    fun saveLog() {
        viewModelScope.launch {
            repository.putLog(
                situation = situation.value ?: "",
                emotions = selectedEmotions.value ?: listOf(),
                thoughts = thoughts.value ?: listOf()
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        //todo feels weird for the view model to be in charge of this when it's not interacting with the db in any other way.  maybe a lifecycle observer could do this better
        realm.get().close()
    }
}

/**
 * Adding to a list doesn't cause an emission, so we gotta make one.
 */
fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}

inline fun <X, Y> LiveData<X>.mapOrNull(crossinline transform: (X) -> Y): LiveData<Y>? {
    return if (this.value == null) {
        null
    } else {
        map(transform)
    }
}
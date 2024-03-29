package com.mindbuilders.cognitivemoodlog.view

import android.os.Bundle
import androidx.lifecycle.*
import com.mindbuilders.cognitivemoodlog.data.LogRepository
import com.mindbuilders.cognitivemoodlog.model.*
import dagger.Lazy
import io.realm.Realm
import kotlinx.coroutines.launch

/**
 * This class is in charge of the entire vie
 */
class LogViewModel constructor(
    val repository: LogRepository,
    private val realm: Lazy<Realm>,
    private val handle: SavedStateHandle
) :
    ViewModel() {

    //situation
    private val _situation: MutableLiveData<String> = MutableLiveData(handle.get("situation") ?: "")
    val situation: LiveData<String> = _situation

    //last nav
    val lastNav: LiveData<Bundle> = handle.getLiveData("lastRoute")

    //emotions
    private val _emotionList: MutableLiveData<List<Emotion>> = MutableLiveData(emptyList())
    val emotionList: LiveData<List<Emotion>> = _emotionList

    val groupedEmotions: LiveData<Map<String, List<Emotion>>> =
        emotionList.map { emotionList -> emotionList.groupBy { it.category } }
    val selectedEmotions: LiveData<List<Emotion>> =
        emotionList.map { emotionList -> emotionList.filter { it.strengthBefore > 0 } }

    //thoughts
    private val _thoughts: MutableLiveData<MutableList<Thought>> =
        MutableLiveData(handle.get("thoughts") ?: mutableListOf())
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

    val realmLogEntries: LiveData<List<RealmLogEntry>?> = repository.results.map { it.value }
    fun refreshLogEntries() {
        viewModelScope.launch {
            repository.refreshAndCount()
        }
    }

    //loading status
    private val _isLoading: LiveData<Boolean> = _emotionList.map {
        it.isEmpty()
    }

    //This just acts as an aggregate event listener
    private val isLoadingAggregate: MediatorLiveData<Int> = MediatorLiveData<Int>()

    val isLoading: LiveData<Boolean> = isLoadingAggregate.map {
        return@map _isLoading.value == true
    }.distinctUntilChanged()


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
        isLoadingAggregate.value = 0
        isLoadingAggregate.addSource(_isLoading) {
            isLoadingAggregate.value = isLoadingAggregate.value
        }
        fetchEmotions()
    }
    private fun fetchEmotions() {
        viewModelScope.launch {
            val savedEmotions = handle.get<List<Emotion>>("emotions")
            _emotionList.value = savedEmotions ?: repository.getEmotions()
            _cognitiveDistortionList.value = repository.getCds()
        }
    }
    fun onSituationChange(newSituation: String) {
        handle["situation"] = newSituation
        _situation.value = newSituation
    }

    fun addBeforeThought(newThought: String) {
        val thought = Thought(thoughtBefore = newThought)
        _thoughts.value?.add(thought)
        handle["thoughts"] = _thoughts.value
        _thoughts.notifyObserver()
    }

    fun editEmotion(func: () -> Unit) {
        func.invoke()
        handle["emotions"] = _emotionList.value
        _emotionList.notifyObserver()
    }

    fun editThought(func: () -> Unit) {
        func.invoke()
        handle["thoughts"] = _thoughts.value
        _thoughts.notifyObserver()
    }

    fun clearLog() {
        _thoughts.value = mutableListOf()
        _emotionList.value = mutableListOf()
        _situation.value = ""

        handle.remove<Bundle>("lastRoute")
        handle.remove<String>("situation")
        handle.remove<List<Thought>>("thoughts")
        handle.remove<List<Emotion>>("emotions")

        fetchEmotions()
    }

    fun saveLog() {
        viewModelScope.launch {
            repository.putLog(
                situation = situation.value ?: "",
                emotions = selectedEmotions.value ?: listOf(),
                thoughts = thoughts.value ?: listOf()
            )
            clearLog()
        }

    }

    override fun onCleared() {
        super.onCleared()
        //todo feels weird for the view model to be in charge of this when it's not interacting with the db in any other way.  maybe a lifecycle observer could do this better
        realm.get().close()
    }

    fun updateLastNav(bundle: Bundle) {
        handle["lastRoute"] = bundle
    }
}

/**
 * Adding to a list doesn't cause an emission, so we gotta make one.
 */
fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}
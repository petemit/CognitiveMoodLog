package com.mindbuilders.cognitivemoodlog.view

import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.compose.navigate
import com.mindbuilders.cognitivemoodlog.data.LogRepository

import com.mindbuilders.cognitivemoodlog.model.*
import dagger.Lazy
import io.realm.Realm
import kotlinx.coroutines.launch

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
    val lastNav: LiveData<String> = handle.getLiveData("lastRoute")

    //emotions
    private val _emotionList: MutableLiveData<List<Emotion>> = MutableLiveData()
    val emotionList: LiveData<List<Emotion>> = _emotionList

    val groupedEmotions: LiveData<Map<String, List<Emotion>>> =
        emotionList.map { emotionList -> emotionList.groupBy { it.category } }
    val selectedEmotions: LiveData<List<Emotion>> =
        emotionList.map { emotionList -> emotionList.filter { it.strengthBefore > 0 } }

    //thoughts
    private val _thoughts: MutableLiveData<MutableList<Thought>> = MutableLiveData(handle.get("thoughts") ?: mutableListOf())
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
            val savedEmotions = handle.get<List<Emotion>>("emotions")
            _emotionList.value = savedEmotions ?: repository.getEmotions()
            _cognitiveDistortionList.value = repository.getCds()
            _isLoading.value = false
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

        handle.remove<String>("situation")
        handle.remove<List<Thought>>("thoughts")
        handle.remove<List<Emotion>>("emotions")
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

//    fun navLast(navController: NavController) {
//        val route = handle.get<String>("lastRoute")
//        if (route != null && route.isNotEmpty()) {
//            navController.navigate(route)
//        }
//    }

    fun nav(navController: NavController, route: String) {
        handle["lastRoute"] = route
        navController.navigate(route)
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
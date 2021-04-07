package com.mindbuilders.cognitivemoodlog.data

import android.util.Log
import com.mindbuilders.cognitivemoodlog.model.*
import com.mongodb.realm.livedataquickstart.model.LiveRealmResults
import io.realm.Realm
import io.realm.Sort
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId
import javax.inject.Inject
import dagger.Lazy


class SeedDataRepository @Inject constructor(private val assetFetcher: AssetFetcher, private val realm: Lazy<Realm>) {

    suspend fun getLogs(): LiveRealmResults<LogEntry>  {
        return withContext(Dispatchers.IO) {
            return@withContext LiveRealmResults(realm.get().where(LogEntry::class.java).sort("date", Sort.DESCENDING).findAll())
        }
    }

    suspend fun putLog(situation: String, emotions: List<Emotion>, thoughts: List<Thought>) {
        withContext(Dispatchers.IO) {
            realm.get().executeTransaction { r: Realm ->
                val newLogEntry = r.createObject(LogEntry::class.java, ObjectId())
                newLogEntry.situation = situation
                newLogEntry.emotions.addAll(emotions.map {
                    val realmEmotion = r.createObject(RealmEmotion::class.java, ObjectId())
                    realmEmotion.category = it.category
                    realmEmotion.name = it.name
                    realmEmotion.strengthBefore = it.strengthBefore
                    realmEmotion.strengthAfter = it.strengthAfter
                    realmEmotion
                })
                newLogEntry.thoughts.addAll(thoughts.map {
                    val realmThought = r.createObject(RealmThought::class.java, ObjectId())
                    realmThought.thoughtAfter = it.thoughtAfter
                    realmThought.thoughtBefore = it.thoughtBefore
                    //todo add fetch and then find
                    realmThought.cognitiveDistortion = r.createObject(RealmCognitiveDistortion::class.java, ObjectId())
                    val cd  = it.cognitiveDistortion
                    cd?.let { nonNullCd ->
                        realmThought.cognitiveDistortion?.name = nonNullCd.name
                        realmThought.cognitiveDistortion?.description =
                            nonNullCd.description
                        realmThought.cognitiveDistortion?.example =
                            nonNullCd.example
                        realmThought.cognitiveDistortion?.summary =
                            nonNullCd.summary
                    }
                    realmThought.negBeliefAfter = it.negBeliefAfter
                    realmThought.negBeliefBefore = it.negBeliefBefore
                    realmThought.posBelief = it.posBelief
                    realmThought
                })
            }
        }
    }

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
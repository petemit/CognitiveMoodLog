package com.mindbuilders.cognitivemoodlog.data

import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.*
import android.util.Log
import com.mindbuilders.cognitivemoodlog.model.CognitiveDistortion
import com.mindbuilders.cognitivemoodlog.model.Emotion
import com.mindbuilders.cognitivemoodlog.model.Thought
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LegacyDbMigrator @Inject constructor(
    private val logRepository: LogRepository,
    private val context: Context
) {
    suspend fun migrateDbIfNecessary() {
        if (context.getSharedPreferences()
                ?.getBoolean(NEEDS_MIGRATION, true) == true
        ) {
            try {
                val db = SQLiteDatabase.openDatabase(
                    context.getDatabasePath("CognitiveMoodLog.db").toString(),
                    null,
                    SQLiteDatabase.OPEN_READONLY
                )

                if (db.version < 2) {
                    if (db != null) {
                        //any data in here?
                        if (dbIsEmpty(db)) {
                            return
                        }

                        try {
                            //note:  Working with the query builders was really awful.  SQL is just easier... plus we're moving the data to noSQL so I've got no problem with this.

                            val cursor = db.rawQuery(MIGRATION_SQL,

                                null
                            )

                            val columns = cursor.columnNames
                            val rows = mutableListOf<Map<String, String>>()
                            var rowCounter = 0
                            while (cursor.moveToNext()) {
                                rowCounter++
                                val row = mutableMapOf<String, String>()
                                columns.mapIndexed { index, s ->
                                    row[s] = cursor.getString(index) //?: cursor.getInt(index)
                                }
                                rows.add(row)
                            }
                            cursor.close()

                            organizeRows(rows)

                        } catch (e: Exception) {
                            Log.e(
                                "mindbuilders",
                                "unable to parse database. Exiting ${e.message} ${e.stackTraceToString()}"
                            )
                            return
                        }
                    }
                    println("hey")
                    // throw RuntimeException("Just quit for now until I've got this sorted.")
                }


            } catch (e: SQLiteCantOpenDatabaseException) {
                // no db, no problem.   Don't try this again.
                preventFutureDbMigration()
            }
        }
    }

    private fun dbIsEmpty(db: SQLiteDatabase): Boolean {
        try {
            db.rawQuery("select * from logentry limit 1", null).close()
        } catch (e: Exception) {
            Log.e(
                "mindbuilders",
                "unable to parse database.  Exiting  ${e.message} ${e.stackTraceToString()} "
            )

            return true
        }
        return false
    }


    private suspend fun organizeRows(rows: MutableList<Map<String, String>>) {
//        val logentries = rows.groupBy { it["logentry_id"] }
//        val byEmotionId = logentries.keys.map { logentryid ->
//            logentries[logentryid]?.groupBy { it["emotion_id"] }
//        }
//
//        val byThoughtId = logentries.map { map ->
//            logentries[map.key]?.groupBy { it["thought_id"] }
//        }
//        byThoughtId

        //todo not optimized, but this will only happen once.
        val logEntries = rows.map { it["logentry_id"] }.distinct()

        val emotions = rows.groupBy {
            it["emotion_id"]
        }

        val thoughts = rows.groupBy {
            it["thought_id"]
        }

        if (logEntries.isEmpty() || emotions.isEmpty() || thoughts.isEmpty()) {
            preventFutureDbMigration()
            return
        }

        logEntries.forEach { logEntry_id ->
            var situation = ""
            var date = ""
            val emotionList = emotions.map { emotionMap ->
                emotionMap.value.filter {
                    it["logentry_id"] == logEntry_id
                }
            }
                .flatMap { listOfRowsPerEmotionPerLogEntry ->
                    listOfRowsPerEmotionPerLogEntry.distinctBy { it["emotion_id"] }.map {
                        //nab the situation here
                        situation = it["logentry"] ?: ""
                        date = it["timestamp"] ?: ""

                        val emotionId =
                            it["emotion_id"]?.toInt() ?: 0 //Id doesn't really matter anymore
                        val emotionCategory = it["ecname"] ?: ""
                        val emotionName = it["emotionname"] ?: ""
                        val emotionStrengthBefore = it["beliefbefore"]?.toFloat() ?: 0f
                        val emotionStrengthAfter = it["beliefafter"]?.toFloat() ?: 0f
                        Emotion(
                            id = emotionId,
                            category = emotionCategory,
                            name = emotionName,
                            strengthBefore = emotionStrengthBefore,
                            strengthAfter = emotionStrengthAfter
                        )
                    }
                }
            val thoughtList = thoughts.map { thoughtMap ->
                thoughtMap.value.filter {
                    it["logentry_id"] == logEntry_id
                }
            }
                .flatMap { listOfRowsPerThoughtPerLogEntry ->
                    listOfRowsPerThoughtPerLogEntry.distinctBy { it["thought_id"] }.map {
                        val id = it["thought_id"] ?: ""
                        val thoughtBefore = it["negativethought"] ?: ""
                        val thoughtAfter = it["positivethought"] ?: ""
                        val negBeliefBefore = it["negativebeliefbefore"]?.toFloat() ?: 0f
                        val negBeliefAfter = it["negativebeliefafter"]?.toFloat() ?: 0f
                        val posBelief = it["positivebeliefbefore"]?.toFloat() ?: 0f
                        val cognitiveDistortion = CognitiveDistortion(
                            id = it["cognitivedistortion_id"]?.toInt() ?: 0,
                            name = it["cogname"] ?: "",
                            summary = it["cogdescription"] ?: "",
                            description = "",
                            example = ""
                        )
                        Thought(
                            id = id,
                            thoughtBefore = thoughtBefore,
                            thoughtAfter = thoughtAfter,
                            negBeliefBefore = negBeliefBefore,
                            negBeliefAfter = negBeliefAfter,
                            posBelief = posBelief,
                            cognitiveDistortion = cognitiveDistortion
                        )
                    }
                }
            val sdf = SimpleDateFormat("yyyy-MM-dd h:mm a", Locale.getDefault())

            logRepository.putLog(
                situation = situation,
                emotions = emotionList,
                thoughts = thoughtList,
                date = sdf.parse(date) ?: Calendar.getInstance().time
            )


            //preventFutureDbMigration()
        }
    }

    private fun preventFutureDbMigration() {
        context.getSharedPreferences()?.edit()?.putBoolean(NEEDS_MIGRATION, false)
            ?.apply()
    }


}

fun Context.getSharedPreferences(): SharedPreferences? {
    return getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
}

//Putting these at the bottom to not obscure code... thanks huge SQL block
const val PREF_KEY = "cbtlog"
const val NEEDS_MIGRATION = "needs_migration"
private val MIGRATION_SQL = """
                                    select
                                         l.logentry,
                                         l.timestamp,
                                         t.negativethought,
                                         t.positivethought,
                                         t.logentry_id,
                                         t.negativebeliefbefore,
                                         t.negativebeliefafter,
                                         t.positivebeliefbefore,
                                         tc.thought_id,
                                         tc.cognitivedistortion_id,
                                         cd.name as cogname,
                                         cd.description as cogdescription,
                                         emotion_id, elb.beliefbefore,
                                         elb.beliefafter,
                                         emocatid,
                                         ec.name as ecname,
                                         e.name as emotionname 
                                        from logentry as l 
                                        left join thought as t on l.ROWID = t.logentry_id 
                                        left join thought_cognitivedistortion tc on t.ROWID = tc.thought_id
                                        left join cognitivedistortion cd on tc.cognitivedistortion_id = cd.ROWID
                                        left join emotion_logentry_belief as elb on elb.logentry_id = l.ROWID
                                        left join emotion as e on e.ROWID = elb.emotion_id
                                        left join emotioncategory as ec on e.emotioncategory_id = ec.ROWID
                                """.trimIndent()
package com.mindbuilders.cognitivemoodlog.view

import androidx.annotation.StringRes
import com.mindbuilders.cognitivemoodlog.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object MainMenu : Screen("mainMenu", R.string.title_activity_main)
    object DescribeSituation : Screen("describe_situation", R.string.describe_situation)
    object ReviewLogs : Screen("review_logs", R.string.review_logs)
    object SelectEmotions: Screen("select_emotions", R.string.select_emotions)
    object ThoughtsBefore: Screen("thoughts_before", R.string.thoughts_before)
    object CognitiveDistortions: Screen("cognitive_distortions", R.string.cognitive_distortions)
    object ThoughtsAfter: Screen("thoughts_after", R.string.thoughts_after)
    object ThoughtsReview: Screen("thoughts_review", R.string.thoughts_review)
    object EmotionsAfter: Screen("emotions_after", R.string.emotions_after)
    object LogReview: Screen("log_review", R.string.log_review)
}
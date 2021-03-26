package com.mindbuilders.cognitivemoodlog.view

import androidx.annotation.StringRes
import com.mindbuilders.cognitivemoodlog.R

sealed class Screen(val route: String, @StringRes val resourceId: Int) {
    object MainMenu : Screen("mainMenu", R.string.title_activity_main)
    object DescribeSituation : Screen("describe_situation", R.string.describe_situation)
    object ReviewLogs : Screen("review_logs", R.string.review_logs)
}
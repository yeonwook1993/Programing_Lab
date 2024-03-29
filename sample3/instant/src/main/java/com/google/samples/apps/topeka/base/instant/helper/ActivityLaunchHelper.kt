package com.google.samples.apps.topeka.base.instant.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import com.google.samples.apps.topeka.base.instant.activity.CategorySelectionActivity
import com.google.samples.apps.topeka.base.instant.activity.QuizActivity
import com.google.samples.apps.topeka.base.instant.model.Category

class ActivityLaunchHelper {

    companion object {

        const val EXTRA_EDIT = "EDIT"

        fun launchCategorySelection(activity: Activity, options: ActivityOptionsCompat? = null) {
            val starter = categorySelectionIntent(activity)
            if (options == null) {
                activity.startActivity(starter)
            } else {
                ActivityCompat.startActivity(activity, starter, options.toBundle())
            }
        }

        fun categorySelectionIntent(context: Context? = null) =
                baseIntent(CategorySelectionActivity::class.java, context)

        fun quizIntent(category: Category, context: Context? = null) =
                baseIntent(QuizActivity::class.java, context).apply {
                    putExtra(Category.TAG, category.id) }

        private fun baseIntent(activityClass: Class<out Activity>, context: Context? = null): Intent {
            return Intent(context, activityClass)
        }
    }
}
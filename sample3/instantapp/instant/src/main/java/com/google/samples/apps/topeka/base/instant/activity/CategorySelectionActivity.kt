/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.topeka.base.instant.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.TransitionInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.samples.apps.topeka.base.instant.R
import com.google.samples.apps.topeka.base.instant.fragment.CategorySelectionFragment
import com.google.samples.apps.topeka.base.instant.helper.ActivityLaunchHelper
import com.google.samples.apps.topeka.base.instant.helper.ApiLevelHelper
import com.google.samples.apps.topeka.base.instant.helper.REQUEST_LOGIN
import com.google.samples.apps.topeka.base.instant.helper.REQUEST_SAVE
import com.google.samples.apps.topeka.base.instant.helper.database
import com.google.samples.apps.topeka.base.instant.helper.findFragmentById
import com.google.samples.apps.topeka.base.instant.helper.logout
import com.google.samples.apps.topeka.base.instant.helper.onSmartLockResult
import com.google.samples.apps.topeka.base.instant.helper.replaceFragment
import com.google.samples.apps.topeka.base.instant.helper.requestLogin
import com.google.samples.apps.topeka.base.instant.model.Player
import com.google.samples.apps.topeka.base.instant.widget.AvatarView

class CategorySelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_selection)
        initializePlayer()

        setUpToolbar()
        if (savedInstanceState == null) {
            attachCategoryGridFragment()
        } else {
            setProgressBarVisibility(View.GONE)
        }
        supportPostponeEnterTransition()
    }

    private fun updatePlayerViews(player: Player) {
        findViewById<TextView>(R.id.title).setText(player.toString())
        player.avatar?.run { findViewById<AvatarView>(R.id.avatar).setAvatar(this) }
    }

    private fun initializePlayer() = requestLogin { updatePlayerViews(it) }

    private fun setUpToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar_player))
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun attachCategoryGridFragment() {
        replaceFragment(R.id.category_container,
                findFragmentById(R.id.category_container) ?: CategorySelectionFragment())
        setProgressBarVisibility(View.GONE)
    }

    private fun setProgressBarVisibility(visibility: Int) {
        findViewById<View>(R.id.progress).visibility = visibility
    }

    override fun onResume() {
        super.onResume()
        (findViewById<TextView>(R.id.score)).text =
                getString(R.string.x_points, database().getScore())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_category, menu)
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CategorySelectionFragment.REQUEST_CATEGORY) {
            data?.let {
                findFragmentById(R.id.category_container)?.run {
                    onActivityResult(requestCode, resultCode, data)
                }
            }
        } else if (requestCode == REQUEST_LOGIN || requestCode == REQUEST_SAVE) {
            onSmartLockResult(
                    requestCode,
                    resultCode,
                    data,
                    success = { },
                    failure = { requestLogin { updatePlayerViews(it) } })
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.sign_out) {
            handleSignOut()
            true
        } else super.onOptionsItemSelected(item)
    }

    @SuppressLint("NewApi")
    private fun handleSignOut() {
        if (ApiLevelHelper.isAtLeast(Build.VERSION_CODES.LOLLIPOP)) {
            window.exitTransition = TransitionInflater.from(this)
                    .inflateTransition(R.transition.category_enter)
        }
        logout()
        ActivityLaunchHelper.launchSignIn(this, true)
        supportFinishAfterTransition()
    }
}


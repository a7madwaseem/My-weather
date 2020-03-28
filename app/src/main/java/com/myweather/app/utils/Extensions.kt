package com.myweather.app.utils

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Ahmad Kazimi on 27/03/2020
 */

fun AppCompatActivity.setupToolbar(toolbar: Toolbar) {

    setSupportActionBar(toolbar)

    if (supportActionBar != null) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
    }

    // Setup toolbar with navigation host
    NavigationUI.setupWithNavController(
        toolbar,
        NavHostFragment.findNavController(nav_host_fragment)
    )
}

fun Fragment.checkPermission(activity: FragmentActivity, permission: String): Boolean {
    val hasPermission = ContextCompat.checkSelfPermission(
        activity.applicationContext,
        permission
    ) == PackageManager.PERMISSION_GRANTED
    if (!hasPermission) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), 0)
    }
    return hasPermission
}
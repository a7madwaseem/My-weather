package com.myweather.app.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.inputmethod.InputMethodManager

/**
 * Created by Ahmad Kazimi on 27/03/2020
 */
class InputUtils {

    companion object {
        @JvmStatic
        fun hideKeyboard(activity: Activity?) {
            if (activity == null) return
            try {
                val currentFocus = activity.currentFocus
                if (currentFocus != null) {
                    val inputManager =
                        activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(
                        currentFocus.windowToken,
                        InputMethodManager.HIDE_NOT_ALWAYS
                    )
                }
            } catch (e: Exception) {
                Log.e("InputUtils", e.localizedMessage!!)
            }
        }
    }
}
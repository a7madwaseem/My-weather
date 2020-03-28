package com.myweather.app.ui.activity.main

import android.os.Bundle
import com.myweather.app.R
import com.myweather.app.ui.activity.BasicActivity
import com.myweather.app.utils.setupToolbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BasicActivity() {

    //region Activity Methods
    override fun getContentResource(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupToolbar(toolbar)
    }
    //endregion
}

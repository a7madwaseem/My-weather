package com.myweather.app.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by Ahmad Kazimi on 27/03/2020
 */
abstract class BasicActivity : AppCompatActivity() {

    //region Activity Methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentResource())
    }

    abstract fun getContentResource(): Int;
    //endregion
}
package com.myweather.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Created by Ahmad Kazimi on 28/03/2020
 */
abstract class BasicFragment : Fragment() {

    lateinit var dataBinding: ViewDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(context).inflate(getContentResource(), container, false)
    }

    abstract fun getContentResource(): Int
}
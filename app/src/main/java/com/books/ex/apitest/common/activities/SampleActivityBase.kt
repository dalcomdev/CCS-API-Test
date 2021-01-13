package com.books.ex.apitest.common.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.books.ex.apitest.common.logger.LogHelper
import com.books.ex.apitest.common.logger.LogWrapper

open class SampleActivityBase : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        initializeLogging()
    }

    open fun initializeLogging() {
        val logWrapper = LogWrapper()
        LogHelper.logNode = logWrapper
        LogHelper.i(TAG, "Ready")
    }

    companion object {
        const val TAG = "SampleActivityBase"
    }
}
package com.books.ex.apitest.apiclient

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.books.ex.apitest.R
import com.books.ex.apitest.common.activities.SampleActivityBase
import com.books.ex.apitest.common.logger.LogFragment
import com.books.ex.apitest.common.logger.LogHelper
import com.books.ex.apitest.common.logger.LogWrapper
import com.books.ex.apitest.common.logger.MessageOnlyLogFilter


class MainActivity : SampleActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) == null) {
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            val fragment = ApiClientFragment()
            transaction.add(fragment, FRAGMENT_TAG)
            transaction.commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun initializeLogging() {
        val logWrapper = LogWrapper()
        LogHelper.setLogNode(logWrapper)

        val msgFilter = MessageOnlyLogFilter()
        logWrapper.next = msgFilter

//        val logFragment: LogFragment? = supportFragmentManager
//                .findFragmentById(R.id.log_fragment)

        (supportFragmentManager.findFragmentById(R.id.log_fragment) as LogFragment?)?.let {
            msgFilter.next = it.logView
            it.logView.setTextAppearance(this, R.style.LogHelper)
        }
        LogHelper.i(TAG, "Ready")
    }

    companion object {
        const val TAG = "MainActivity"
        const val FRAGMENT_TAG = "ApiClientFragment"
    }
}
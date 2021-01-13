package com.books.ex.apitest.apiclient

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentTransaction
import com.books.ex.apitest.R
import com.books.ex.apitest.common.activities.SampleActivityBase
import com.books.ex.apitest.common.logger.LogFragment
import com.books.ex.apitest.common.logger.LogHelper
import com.books.ex.apitest.common.logger.LogWrapper
import com.books.ex.apitest.common.logger.MessageOnlyLogFilter
import com.books.ex.apitest.common.network.ConcurrentTask
import com.books.ex.apitest.common.network.RxJavaTask
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger


class MainActivity : SampleActivityBase() {

    private val sThreadFactory: ThreadFactory = object : ThreadFactory {
        private val mCount = AtomicInteger(1)
        override fun newThread(r: Runnable): Thread {
            return Thread(r, "AsyncTask #" + mCount.getAndIncrement())
        }
    }
    private val mExecutorService = Executors.newFixedThreadPool(10, sThreadFactory)

    var repeatCount: Int
        get() = runCatching {
            return repeat_count.text.toString().toInt()
        }.getOrDefault(0)
        set(value) {
            repeat_count.setText(value.toString())
        }

    var startTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!hasPermissions(this, *permissions)) {
            getPermissions()
        } else {
            init()
        }
    }

    private fun init() {
        // concurrent call
        concurrent_call.setOnClickListener {
            enableButtons(false)
            startTime = System.currentTimeMillis()
            LogHelper.i(TAG, LINE_BREAK)
            concurrentApiCall(repeatCount)
        }

        // rxjava call
        rxjava_call.setOnClickListener {
            enableButtons(false)
            startTime = System.currentTimeMillis()
            LogHelper.i(TAG, LINE_BREAK)
            rxJavaApiCall(repeatCount)
        }
    }

    private fun concurrentApiCall(repeat: Int) {
        callApi(repeat) { i ->
            runCatching {
                ConcurrentTask.call(i, mExecutorService).thenApply {
                    LogHelper.i(TAG, "Concurrent Task #$it finished.")
                    timeElapsed()
                }
            }.onFailure {
                Thread.currentThread().interrupt()
            }
        }
    }

    private fun rxJavaApiCall(repeat: Int) {
        callApi(repeat) { i ->
            RxJavaTask.singleCall(i)
                .subscribeOn(Schedulers.io())
                .subscribe({ index ->
                    LogHelper.i(TAG, "RxJava Task #$index finished.")
                    timeElapsed()
                }, { error ->
                    processError(error)
                })
        }
    }

    private fun processError(e: Throwable?) {
        e?.apply {
            LogHelper.e(TAG, LINE_BREAK)
            LogHelper.e(TAG, "${e.message}")
            LogHelper.e(TAG, LINE_BREAK)
        }
    }

    @UiThread
    private fun enableButtons(b: Boolean) {
        runOnUiThread {
            concurrent_call.isEnabled = b
            rxjava_call.isEnabled = b
        }
    }

    private fun callApi(repeat: Int, f: (index: Int) -> Unit) {
        for (i in 1..repeat) {
            f(i)
        }
    }

    private fun timeElapsed() {
        if (endCounter.incrementAndGet() == repeatCount) {
            LogHelper.i(TAG, LINE_BREAK)
            LogHelper.i(TAG, "Time taken: ${System.currentTimeMillis() - startTime} ms")

            // reset variables, buttons
            startTime = 0
            endCounter.set(0)
            enableButtons(true)
        }
    }

    override fun initializeLogging() {
        val logWrapper = LogWrapper()
        LogHelper.logNode = logWrapper

        val msgFilter = MessageOnlyLogFilter()
        logWrapper.next = msgFilter

        (supportFragmentManager.findFragmentById(R.id.log_fragment) as LogFragment?)?.let {
            msgFilter.next = it.logView
            it.logView.setTextAppearance(this, R.style.LogHelper)
        }
        LogHelper.i(TAG, "Ready")
    }

    private var permissions = arrayOf(
        Manifest.permission.READ_PHONE_STATE
    )

    private fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission!!
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    private fun getPermissions() =
        ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS)

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            when {
                grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED -> {
                    Toast.makeText(
                        this,
                        "Permission: ${permissions[0]} is granted",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        const val TAG = "MainActivity"
        const val REQUEST_PERMISSIONS = 1000
        const val DATA_TIMEOUT = 10 * 1000
        val endCounter = AtomicInteger(0)
        const val LINE_BREAK = "-----------------------------"
        const val CCS_TEST_URL =
            "https://qa-search.onestore.co.kr/api/common/search?query=킹오브파이터&offset=0&count=20"
    }
}
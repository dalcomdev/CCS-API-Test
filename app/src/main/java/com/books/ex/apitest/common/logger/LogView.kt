package com.books.ex.apitest.common.logger

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatTextView

class LogView : AppCompatTextView, LogNode {
    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!, attrs
    )

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context!!, attrs, defStyle
    )

    override fun println(priority: Int, tag: String?, msg: String?, tr: Throwable?) {
        var priorityStr: String? = null
        when (priority) {
            Log.VERBOSE -> priorityStr = "VERBOSE"
            Log.DEBUG -> priorityStr = "DEBUG"
            Log.INFO -> priorityStr = "INFO"
            Log.WARN -> priorityStr = "WARN"
            Log.ERROR -> priorityStr = "ERROR"
            Log.ASSERT -> priorityStr = "ASSERT"
            else -> { }
        }
        var exceptionStr: String? = null
        if (tr != null) {
            exceptionStr = Log.getStackTraceString(tr)
        }
        val outputBuilder = StringBuilder()
        val delimiter = "\t"
        appendIfNotNull(outputBuilder, priorityStr, delimiter)
        appendIfNotNull(outputBuilder, tag, delimiter)
        appendIfNotNull(outputBuilder, msg, delimiter)
        appendIfNotNull(outputBuilder, exceptionStr, delimiter)
        (context as Activity).runOnUiThread(Thread { appendToLog(outputBuilder.toString()) })
        if (next != null) {
            next!!.println(priority, tag, msg, tr)
        }
    }

    private fun appendIfNotNull(
        source: StringBuilder,
        addStr: String?,
        delimiter: String
    ): StringBuilder {
        var delimiter: String? = delimiter
        if (addStr != null) {
            if (addStr.isEmpty()) {
                delimiter = ""
            }
            return source.append(addStr).append(delimiter)
        }
        return source
    }

    var next: LogNode? = null
    private fun appendToLog(s: String) {
        append("\n$s")
    }
}
package com.books.ex.apitest.common.logger

import android.util.Log

object LogHelper {
    const val NONE = -1
    const val VERBOSE = Log.VERBOSE
    const val DEBUG = Log.DEBUG
    const val INFO = Log.INFO
    const val WARN = Log.WARN
    const val ERROR = Log.ERROR
    const val ASSERT = Log.ASSERT
    var logNode: LogNode? = null

    @JvmOverloads
    fun println(priority: Int, tag: String?, msg: String?, tr: Throwable? = null) {
        logNode?.let {
            logNode!!.println(priority, tag, msg, tr)

        }
        Log.d(tag, msg!!)
    }

    @JvmOverloads
    fun v(tag: String?, msg: String?, tr: Throwable? = null) {
        println(VERBOSE, tag, msg, tr)
    }

    @JvmOverloads
    fun d(tag: String?, msg: String?, tr: Throwable? = null) {
        println(DEBUG, tag, msg, tr)
    }

    @JvmOverloads
    fun i(tag: String?, msg: String?, tr: Throwable? = null) {
        println(INFO, tag, msg, tr)
    }

    @JvmOverloads
    fun w(tag: String?, msg: String?, tr: Throwable? = null) {
        println(WARN, tag, msg, tr)
    }

    fun w(tag: String?, tr: Throwable?) {
        w(tag, null, tr)
    }

    @JvmOverloads
    fun e(tag: String?, msg: String?, tr: Throwable? = null) {
        println(ERROR, tag, msg, tr)
    }

    @JvmOverloads
    fun wtf(tag: String?, msg: String?, tr: Throwable? = null) {
        println(ASSERT, tag, msg, tr)
    }

    fun wtf(tag: String?, tr: Throwable?) {
        wtf(tag, null, tr)
    }
}
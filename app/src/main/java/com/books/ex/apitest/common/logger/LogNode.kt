package com.books.ex.apitest.common.logger

interface LogNode {
    fun println(priority: Int, tag: String?, msg: String?, tr: Throwable?)
}
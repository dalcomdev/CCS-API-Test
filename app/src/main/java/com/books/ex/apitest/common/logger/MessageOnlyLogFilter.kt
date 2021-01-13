package com.books.ex.apitest.common.logger

class MessageOnlyLogFilter : LogNode {
    var next: LogNode? = null

    constructor(next: LogNode?) {
        this.next = next
    }

    constructor() {}

    override fun println(priority: Int, tag: String?, msg: String?, tr: Throwable?) {
        if (next != null) {
            next!!.println(LogHelper.NONE, null, msg, null)
        }
    }
}
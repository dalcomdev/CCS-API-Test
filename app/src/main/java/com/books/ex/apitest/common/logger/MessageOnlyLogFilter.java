package com.books.ex.apitest.common.logger;

public class MessageOnlyLogFilter implements LogNode {

    LogNode mNext;

    public MessageOnlyLogFilter(LogNode next) {
        mNext = next;
    }

    public MessageOnlyLogFilter() {
    }

    @Override
    public void println(int priority, String tag, String msg, Throwable tr) {
        if (mNext != null) {
            getNext().println(LogHelper.NONE, null, msg, null);
        }
    }

    public LogNode getNext() {
        return mNext;
    }

    public void setNext(LogNode node) {
        mNext = node;
    }

}

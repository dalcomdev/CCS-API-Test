package com.books.ex.apitest.common.network

import com.books.ex.apitest.apiclient.MainActivity
import com.books.ex.apitest.common.logger.LogHelper
import com.onestore.api.manager.StoreApiManager
import io.reactivex.rxjava3.core.Single


class RxJavaTask {
    companion object {
        fun singleCall(index: Int): Single<Int> {
            return Single.create { subscriber ->
                try {
                    val api = StoreApiManager.getInstance().oneStoreDeveloperOpenApi
                    val result = api.searchAppGame(MainActivity.DATA_TIMEOUT, MainActivity.CCS_TEST_URL)
                    subscriber.onSuccess(index)
                } catch (e: Exception) {
                    LogHelper.i(MainActivity.TAG, "${e.localizedMessage} ${e.cause}")
                    subscriber.onError(e)
                }
            }
        }
    }
}
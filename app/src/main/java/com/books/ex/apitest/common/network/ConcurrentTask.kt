package com.books.ex.apitest.common.network

import android.app.Activity
import com.books.ex.apitest.apiclient.MainActivity
import com.onestore.api.manager.StoreApiManager
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

class ConcurrentTask {

    companion object {
        @JvmStatic
        fun call(index: Int, executorService: ExecutorService): CompletableFuture<Int> =
            CompletableFuture.supplyAsync(
                {
                    val api = StoreApiManager.getInstance().oneStoreDeveloperOpenApi
                    val result =
                        api.searchAppGame(MainActivity.DATA_TIMEOUT, MainActivity.CCS_TEST_URL)
                    index
                }, executorService
            )
    }

}
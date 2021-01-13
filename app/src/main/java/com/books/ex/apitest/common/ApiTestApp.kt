package com.books.ex.apitest.common

import android.app.Application
import com.books.ex.apitest.common.network.ApiManager
import com.onestore.api.manager.StoreApiManager

class ApiTestApp : Application() {
    override fun onCreate() {
        super.onCreate()

        ApiManager.getInstance(this)
    }
}
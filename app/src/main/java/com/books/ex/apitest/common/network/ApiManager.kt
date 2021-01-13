package com.books.ex.apitest.common.network

import android.content.Context
import com.onestore.api.manager.StoreApiManager
import com.onestore.api.model.exception.InvalidParameterValueException
import com.skplanet.android.common.net.HttpClient
import com.skplanet.android.common.net.SkpHttpRequest
import com.skplanet.android.common.net.SkpHttpResponse
import com.skplanet.android.shopclient.common.net.StoreApiHttpClient

class ApiManager private constructor(context: Context) : StoreApiManager.ApiContext,
    HttpClient.HttpClientListener {

    override fun setDebugLogData(
        skpHttpRequest: SkpHttpRequest?,
        skpHttpResponse: SkpHttpResponse?,
        var3: Long
    ) {
    }

    override fun clearStatisticsData() {
        //not Implements
    }

    override fun getServiceName(): String {
        return ONESTORE_SERVICE_NAME
    }

    override fun getIDLPOCPackageName(): String {
        return ""
    }

    override fun getSessionId(): String {
        return ""
    }

    override fun getMbrNo(): String {
        return ""
    }

    companion object {
        const val ONESTORE_SERVICE_NAME = "OneStore-Ebook2"
        const val EMUL_DATA = "{" +
                "\"HOSTCCSTYPE\":\"QA1\",\n" +
                "\"HOSTCCSINDEX\":\"SKT QA1\",\n" +
                "\"HOSTCCS\":\"qa-ccs-store.onestore.co.kr\",\n" +
                "\"HOSTSGCTYPE\":\"QA1\",\n" +
                "\"HOSTSGCINDEX\":\"SKT QA1\",\n" +
                "\"HOSTSGC\":\"qa-sgc.onestore.co.kr\",\n" +
                "\"HOSTBMSTYPE\":\"QA1\",\n" +
                "\"HOSTBMSINDEX\":\"SKT QA1\",\n" +
                "\"HOSTBMS\":\"qa-bms.onestore.co.kr\",\n" +
                "\"HOSTIAPQUERY\":\"http://qa.payplanet.co.kr/cm/api/sdk/service/queryV2\",\n" +
                "\"HOSTIAPLOG\":\"https://qa-log.onestore.co.kr\",\n" +
                "\"HOSTBILLINGSANDBOX\":\"https://qa-sbpp.onestore.co.kr\",\n" +
                "\"HOSTMOBILE\":\"qa-m.onestore.co.kr\",\n" +
                "\"HOSTMOBILESHORT\":\"https://onesto.re\",\n" +
                "\"HOSTAPICENTER\":\"https://qa-sdkapis.onestore.co.kr:443/\",\n" +
                "\"HOSTAPICENTERSANDBOX\":\"https://qa-sbpp.onestore.co.kr:443/\",\n" +
                "\"HOSTONEBOOKSMOBILE\":\"qa-m.onestorebooks.co.kr\",\n" +
                "\"HOSTTA\":\"https://qa-dnta.onestore.co.kr/dnta\",\n" +
                "\"HOSTPUSHPLANET\":\"https://qa-push.onestore.co.kr/ompaom/PLANET_CLIENT/\",\n" +
                "\"HOSTDMPCERTIFICATE\":\"https://qa-dmp-cert.onestore.co.kr/dmp/cert/v1\",\n" +
                "\"HOSTDMP\":\"https://qa-dmp.onestore.co.kr/log/v2\",\n" +
                "\"HOSTDMPAPP\":\"https://qa-dmp.onestore.co.kr/onestoreapp/v1\",\n" +
                "\"HOSTDMPDOMAIN\":\"https://qa-dmp.onestore.co.kr/\",\n" +
                "\"HOSTSAMSUNGAPPSALESTATUS\":\"https://stg-vas.samsungapps.com/product/getOneStoreAppStatus.as\",\n" +
                "\"HOSTPUSHONESTORE\":\"https://qa-push.onestore.co.kr/ompaom/push/notification\"\n" +
                "}"
        private var instance: ApiManager? = null

        @JvmStatic
        fun getInstance(context: Context): ApiManager? {
            if (instance == null) {
                instance = ApiManager(context)
            }
            return instance
        }
    }

    init {
        try {
            StoreApiManager.createStoreApiManager(context, this, EMUL_DATA)
//            StoreApiManager.getInstance().initializeCCSHost(CCS_BASE_URL, null)
        } catch (e: InvalidParameterValueException) {
            e.printStackTrace()
        }
        StoreApiHttpClient.getInstance(this)
    }
}
package com.hvuitsme.zlpay

import android.util.Log
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.TlsVersion
import org.json.JSONObject
import java.util.Collections
import java.util.concurrent.TimeUnit

object HttpProvider {
    fun sendPost(URL: String, formBody: RequestBody): JSONObject {
        var data = JSONObject()
        try {
            val spec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                    CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256
                )
                .build()

            val client = OkHttpClient.Builder()
                .connectionSpecs(Collections.singletonList(spec))
                .callTimeout(5, TimeUnit.SECONDS)
                .build()

            val request: Request = Request.Builder()
                .url(URL)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(formBody)
                .build()

            val response: Response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                Log.e("BAD_REQUEST", response.body?.string() ?: "")
                data = JSONObject()
            } else {
                data = JSONObject(response.body?.string())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return data
    }
}
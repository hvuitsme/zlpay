package com.hvuitsme.zlpay

import com.hvuitsme.zlpay.HMacUtil.HMACSHA256
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*

object Helpers {
    private var transIdDefault = 1

    fun getAppTransId(): String {
        if (transIdDefault >= 100000) {
            transIdDefault = 1
        }
        transIdDefault++
        val formatDateTime = SimpleDateFormat("yyMMdd_HHmmss", Locale.getDefault())
        val timeString = formatDateTime.format(Date())
        return String.format("%s%06d", timeString, transIdDefault)
    }

    @Throws(NoSuchAlgorithmException::class, InvalidKeyException::class)
    fun getMac(key: String, data: String): String {
        return HMacUtil.hMacHexStringEncode(HMACSHA256, key, data)
            ?: throw Exception("HMAC calculation failed")
    }
}

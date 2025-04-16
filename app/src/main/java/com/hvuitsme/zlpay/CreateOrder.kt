package com.hvuitsme.zlpay

import okhttp3.FormBody
import okhttp3.RequestBody
import org.json.JSONObject

class CreateOrder {
    private inner class CreateOrderData(amount: String) {
        val AppId: String = AppInfo.APP_ID.toString()
        val AppUser: String = "Android_Demo"
        val AppTime: String = System.currentTimeMillis().toString()
        val Amount: String = amount
        val AppTransId: String = Helpers.getAppTransId()
        val EmbedData: String = "{}"
        val Items: String = "[]"
        val BankCode: String = "zalopayapp"
        val Description: String = "Merchant pay for order #$AppTransId"
        val Mac: String = Helpers.getMac(AppInfo.MAC_KEY, "$AppId|$AppTransId|$AppUser|$Amount|$AppTime|$EmbedData|$Items")
    }

    @Throws(Exception::class)
    fun createOrder(amount: String): JSONObject {
        val input = CreateOrderData(amount)
        val formBody: RequestBody = FormBody.Builder()
            .add("app_id", input.AppId)
            .add("app_user", input.AppUser)
            .add("app_time", input.AppTime)
            .add("amount", input.Amount)
            .add("app_trans_id", input.AppTransId)
            .add("embed_data", input.EmbedData)
            .add("item", input.Items)
            .add("bank_code", input.BankCode)
            .add("description", input.Description)
            .add("mac", input.Mac)
            .build()

        return HttpProvider.sendPost(AppInfo.URL_CREATE_ORDER, formBody)
    }
}
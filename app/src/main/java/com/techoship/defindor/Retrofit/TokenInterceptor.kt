package com.techoship.myposts.Api

import android.content.Context
import com.techoship.defindor.Utils.AppSharedPrefEncrypted
import com.techoship.defindor.Utils.Const
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.IOException


class TokenInterceptor(context: Context) : Interceptor {
    var sharedPref= AppSharedPrefEncrypted(context)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        //rewrite the request to add bearer token
        val newRequest: Request = chain.request().newBuilder()
            .header("Authorization", "${sharedPref.get(Const.SP_TOKEN_TYPE)} ${sharedPref.get(Const.SP_TOKEN)}")
            .build()
        return chain.proceed(newRequest)
    }
}
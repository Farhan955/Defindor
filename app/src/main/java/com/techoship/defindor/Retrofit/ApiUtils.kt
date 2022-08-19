package com.techoship.testretrofit2.Api

import android.content.Context
import com.techoship.defindor.Utils.AppSharedPrefEncrypted
import com.techoship.defindor.Utils.Const
import com.techoship.myposts.Api.TokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiUtils {

    //    private const val API_BASE_URL="http://192.168.109.248:8000/"
//    private const val API_BASE_URL="https://defindor-backend.herokuapp.com/api/"
    private const val API_BASE_URL = "https://defindorv.com/api/"

    private fun getInstance(context: Context): Retrofit {

        // Authrizaion Class (Token)
        val interceptor = TokenInterceptor(context)


        // To log data/Responce so you can view it to understand data
        val loger = HttpLoggingInterceptor()
        loger.level = HttpLoggingInterceptor.Level.BODY

        // Ok Http Clint
        val clint = if (AppSharedPrefEncrypted(context)[Const.SP_TOKEN]!!.isNotEmpty()) {
            OkHttpClient.Builder().addInterceptor(loger).addInterceptor(interceptor).build()
        } else {
            OkHttpClient.Builder().addInterceptor(loger).build()
        }


        // Retrofit Instance
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clint)
            .build()
    }


    // TO use Retrofit
    fun getApiSerevice(context: Context): ApiInterface {
        return getInstance(context).create(ApiInterface::class.java)
    }
}
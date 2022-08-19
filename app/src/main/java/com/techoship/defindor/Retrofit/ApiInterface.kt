package com.techoship.testretrofit2.Api

import com.techoship.defindor.Models.Exchange
import com.techoship.defindor.Models.ExchangeResponse
import com.techoship.defindor.Models.Profile
import com.techoship.defindor.Models.SignupResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {

//    @POST("/users/register")
//    fun registerProfile(@Body profile: Profile?): Call<SignupResponse?>?

    @POST("users/register")
    fun registerProfile(@Body credencials: Profile): Call<SignupResponse>




    @GET("exchanges")
    fun getExchanges(): Call<ExchangeResponse>


    @POST("vaults/create")
    fun createVault(): Call<Response<Any>>



    @POST("vaults/create")
    fun fetchPosts(@Header("Authorization") token: String): Call<Response<Any>>



    /* @GET("v1/phone")
     suspend fun getAllPhoneNumbers():List<PhoneNumberItem>


     @FormUrlEncoded
     @POST("v1/phone")
     suspend fun setPhoneNumer(
         @Field("name") name:String,
         @Field("phoneNo") phoneNo:Long,
     ):Response<PhoneNumberItem>*/


    /* @POST("/api/v1/create")
     suspend fun createEmployee(@Body requestBody: RequestBody): Response<ResponseBody>*/


//
//
//    @FormUrlEncoded
//    @POST("/login")
//    fun signin(
//        @Field("username") email:String,
//        @Field("password") password:String
//    ):Call<ApiAuthResponce>
//
//    @GET("/posts")
//    fun getAllPost():Call<PostResponce>
}
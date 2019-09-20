package com.freedom.fundall.api

import com.freedom.fundall.model.AuthResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {

    @POST("login")
    @FormUrlEncoded
    suspend fun login(@Field("email")email:String,@Field("password")password:String):Response<AuthResponse>


    @POST("register")
    @FormUrlEncoded
    suspend fun register(@Field("firstname")firstname:String,@Field("lastname")lastname:String,
                         @Field("email")email: String,@Field("password")password: String,@Field("password_confirmation")confirmpassword:String
    ):Response<AuthResponse>


    @GET("base/profile")
    suspend fun getUser(@Header("Authorization")authorization:String):Response<AuthResponse>

    @POST("base/profile")
    @Headers("Content-Type","multipart/form-data")
    suspend fun updateAvatar(@Header("Authorization") authorization: String)

}
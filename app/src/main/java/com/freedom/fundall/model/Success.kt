package com.freedom.fundall.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Success(

    @SerializedName("status")
    @Expose
    val status: String,

    val url:String,

    val message:String,

    val data: Data,

    val user: User
)
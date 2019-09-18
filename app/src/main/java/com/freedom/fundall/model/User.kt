package com.freedom.fundall.model

data class User(
    val access_token: String,
    val avatar: String,
    val created_at: String,
    var email: String,
    val expires_at: String,
    val firstname: String,
    val id: Int,
    val lastname: String,
    val monthly_target: Int,
    val token_type: String,
    val updated_at: String
)
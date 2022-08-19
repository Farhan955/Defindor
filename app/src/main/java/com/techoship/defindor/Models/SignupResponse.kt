package com.techoship.defindor.Models

/**
 * Created by FA on 6/11/2022.
 */
data class SignupResponse(
    var success: Boolean? = false,
    var message: String? = "",
    var token: String? = "",
    var email_token: Int?=0,
    )
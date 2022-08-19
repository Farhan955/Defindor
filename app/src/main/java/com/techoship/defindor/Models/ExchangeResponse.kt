package com.techoship.defindor.Models

data class ExchangeResponse(
    val success: Boolean,
    val message: String,
    val data: List<Exchange>,

)
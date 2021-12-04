package com.example.projekt2.data

data class currencyData(
    var base: String?,
    var date: String?,
    var rates: Rates?=null,
    var success: Boolean?,
    var timestamp: Int?
)
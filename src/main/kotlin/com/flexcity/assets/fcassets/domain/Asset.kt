package com.flexcity.assets.fcassets.domain

import java.time.LocalDate

data class Asset (
    val code: String,
    val name: String,
    val activationCost: Double,
    val availability: List<LocalDate>,
    val volume: Int
)
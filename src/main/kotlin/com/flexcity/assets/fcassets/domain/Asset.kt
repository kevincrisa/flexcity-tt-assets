package com.flexcity.assets.fcassets.domain

import java.time.LocalDate

/**
 * Represents an asset in the system.
 */
data class Asset (
    val code: String,
    val name: String,
    val activationCost: Double,
    val availableDates: List<LocalDate>,
    val volume: Int
)
package com.flexcity.assets.fcassets.domain

import java.time.LocalDate

/**
 * Data class representing an asset in the system.
 *
 * An asset can be activated for a given date if it is available
 * and if it meets the requirements of the request (optimization of total activation cost).
 */
data class Asset (
    // Identifier of the asset (unique)
    val code: String,
    // Name of the asset
    val name: String,
    // Cost of asset activation (same price regardless the volume used -> fixed price)
    val activationCost: Double,
    // List of dates when the asset is available
    val availability: List<LocalDate>,
    // Volume available for the asset
    val volume: Int
)
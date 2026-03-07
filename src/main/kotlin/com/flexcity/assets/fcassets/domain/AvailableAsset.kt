package com.flexcity.assets.fcassets.domain

/**
 * Data class representing an asset selected in the system.
 *
 * This is the result of the asset selection process, containing
 * the information needed by the client (code, volume selected, activation cost).
 */
data class AvailableAsset (
    // Identifier of the asset (unique)
    val code: String,
    // Volume allocated for the asset
    val volume: Int,
    // Activation cost of the asset
    val activationCost: Double
)
package com.flexcity.assets.fcassets.domain

/**
 * Represents an asset selected and returned to the client.
 */
data class AvailableAsset (
    val code: String,
    val volume: Int,
    val activationCost: Double
)
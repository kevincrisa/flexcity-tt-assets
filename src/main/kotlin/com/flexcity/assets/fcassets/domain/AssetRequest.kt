package com.flexcity.assets.fcassets.domain

import java.time.LocalDate

/**
 * Represents a request sent by a client to retrieve available assets.
 */
data class AssetRequest (
    val activationDate: LocalDate,
    val requestedVolume: Int,
    val mode: CalculationMode = CalculationMode.RATIO
)
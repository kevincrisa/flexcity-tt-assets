package com.flexcity.assets.fcassets.domain

import java.time.LocalDate

/**
 * Data class representing a request sent by a client to retrieve available assets.
 *
 * The request contains the criteria used to select assets,
 * such as the activation date, the requested volume,
 * and the calculation mode used to determine the best assets.
 */
data class AssetRequest (
    // Date of the activation
    val date: LocalDate,
    // Requested volume
    val volume: Int,
    // Calculation mode to use (Optional because "RATIO" mode is default value)
    val mode: CalculationMode = CalculationMode.RATIO
)
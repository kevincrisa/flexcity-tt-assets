package com.flexcity.assets.fcassets.domain

import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.Min
import java.time.LocalDate

/**
 * Represents a request sent by a client to retrieve available assets.
 */
data class AssetRequest (

    val activationDate: LocalDate,

    @field:Min(1)
    val requestedVolume: Int,

    val mode: CalculationMode = CalculationMode.OPTIMAL
)
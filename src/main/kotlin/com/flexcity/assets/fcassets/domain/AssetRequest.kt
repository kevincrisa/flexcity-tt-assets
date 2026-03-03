package com.flexcity.assets.fcassets.domain

import java.time.LocalDate

data class AssetRequest (
    val date: LocalDate,
    val volume: Int,
    val mode: CalculationMode = CalculationMode.RATIO
)
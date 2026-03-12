package com.flexcity.assets.fcassets.util

import com.flexcity.assets.fcassets.domain.Asset
import com.flexcity.assets.fcassets.domain.AssetAvailability
import java.time.LocalDate

class AssetGeneration {

    fun generateAssets(count: Int): List<Asset> {
        return (1..count).map { itemCount ->
            val randomVolume = (10..100).random()
            val randomCost = (10..500).random().toDouble()
            val numberOfDaysAvailable = (1..7).random()
            val randomDays = (10..16).shuffled().take(numberOfDaysAvailable)
            val asset = Asset(
                code = "A$itemCount",
                name = "Asset $itemCount",
                activationCost = randomCost,
                volume = randomVolume,
                availableDates = emptyList()
            )
            val availabilities = randomDays.map { day ->
                AssetAvailability(id = 0, date = LocalDate.of(2026, 3, day), asset = asset)
            }
            asset.copy(availableDates = availabilities)
        }
    }
}
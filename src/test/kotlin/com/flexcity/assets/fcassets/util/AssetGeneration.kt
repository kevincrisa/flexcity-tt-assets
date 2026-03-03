package com.flexcity.assets.fcassets.util

import com.flexcity.assets.fcassets.domain.Asset
import java.time.LocalDate

class AssetGeneration {

    fun generateAssets(
        count: Int
    ): List<Asset> {
        val assetsGenerated: MutableList<Asset> = mutableListOf()

        for(itemCount in 1..count){
            val randomVolume = (10..100).random()
            val randomCost = (10..500).random().toDouble()
            val numberOfDaysAvailable = (1..7).random()
            val randomDays = (10..16).shuffled().take(numberOfDaysAvailable)

            val daysAvailable: MutableList<LocalDate> = mutableListOf()

            randomDays.forEach { day ->
                daysAvailable.add(LocalDate.of(2026, 3, day))
            }

            val assetGenerated = Asset(
                "A$itemCount",
                "Asset $itemCount",
                randomCost,
                daysAvailable,
                randomVolume
            )

            assetsGenerated.add(assetGenerated)
        }
        return assetsGenerated
    }
}
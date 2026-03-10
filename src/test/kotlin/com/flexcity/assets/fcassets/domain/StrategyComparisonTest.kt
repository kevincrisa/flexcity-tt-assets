package com.flexcity.assets.fcassets.domain

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate
import com.flexcity.assets.fcassets.util.AssetGeneration

class StrategyComparisonTest {

    private val request = AssetRequest(
        activationDate = LocalDate.of(2026,3,10),
        requestedVolume = 100
    )

    @Test
    fun testIfRatioStrategyHaveMinimalCost(){

        val assetGeneration = AssetGeneration()
        val assets = assetGeneration.generateAssets(100)

        val volumeStrategy = AssetsSearchByIncreasingVolume()
        val costStrategy = AssetsSearchByIncreasingCost()
        val ratioStrategy = AssetsSearchByCostPerVolume()

        val volumeStrategySelectedAssets = volumeStrategy.select(assets, request)
        val costStrategySelectedAssets = costStrategy.select(assets, request)
        val ratioStrategySelectedAssets = ratioStrategy.select(assets, request)

        val totalCostForVolumeStrategy = volumeStrategySelectedAssets.sumOf { it.activationCost }
        val totalCostForCostStrategy = costStrategySelectedAssets.sumOf { it.activationCost }
        val totalCostForRatioStrategy = ratioStrategySelectedAssets.sumOf { it.activationCost }

        println("TOTAL FOR VOLUME STRATEGY : $totalCostForVolumeStrategy")
        println("TOTAL FOR COST STRATEGY : $totalCostForCostStrategy")
        println("TOTAL FOR RATIO STRATEGY : $totalCostForRatioStrategy")

        assertTrue(totalCostForRatioStrategy <= totalCostForVolumeStrategy)
    }
}
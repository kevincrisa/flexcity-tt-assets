package com.flexcity.assets.fcassets.domain

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate

class StrategyComparisonTest {

    private val assets = listOf(
        Asset("A1", "Asset 1", 10.0, listOf(LocalDate.of(2026,3,10)), 60),
        Asset("A2", "Asset 2", 9.0, listOf(LocalDate.of(2026,3,10)), 50),
        Asset("A3", "Asset 3", 18.0, listOf(LocalDate.of(2026,3,10)), 100)
    )

    private val request = AssetRequest(
        date = LocalDate.of(2026,3,10),
        volume = 100,
        mode = CalculationMode.RATIO // sera ignoré ici
    )

    @Test
    fun testIfRatioStrategyHaveMinimalCost(){

        val volumeStrategy = AssetsSearchByIncreasingVolume()
        val costStrategy = AssetsSearchByIncreasingCost()
        val ratioStrategy = AssetsSearchByRatioVolumeCost()

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
        assertTrue(totalCostForRatioStrategy <= totalCostForCostStrategy)
    }
}
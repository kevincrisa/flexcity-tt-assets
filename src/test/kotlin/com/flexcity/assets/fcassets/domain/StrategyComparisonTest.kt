package com.flexcity.assets.fcassets.domain

import com.flexcity.assets.fcassets.application.AssetSelectionService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate
import com.flexcity.assets.fcassets.util.AssetGeneration
import org.junit.jupiter.api.BeforeEach

class StrategyComparisonTest {

    private val request = AssetRequest(
        activationDate = LocalDate.of(2026,3,10),
        requestedVolume = 100
    )

    private lateinit var assetSelectionService: AssetSelectionService
    private lateinit var assetVolumeStrategy: AssetsSearchByIncreasingVolume
    private lateinit var assetCostStrategy: AssetsSearchByIncreasingCost
    private lateinit var assetRatioStrategy: AssetsSearchByCostPerVolume

    @BeforeEach
    fun setup() {
        assetSelectionService = AssetSelectionService()
        assetVolumeStrategy = AssetsSearchByIncreasingVolume(assetSelectionService)
        assetCostStrategy = AssetsSearchByIncreasingCost(assetSelectionService)
        assetRatioStrategy = AssetsSearchByCostPerVolume(assetSelectionService)
    }

    @Test
    fun testIfRatioStrategyHaveMinimalCost(){

        val assetGeneration = AssetGeneration()
        val assets = assetGeneration.generateAssets(100)

        val volumeStrategySelectedAssets = assetVolumeStrategy.select(assets, request)
        val costStrategySelectedAssets = assetCostStrategy.select(assets, request)
        val ratioStrategySelectedAssets = assetRatioStrategy.select(assets, request)

        val totalCostForVolumeStrategy = volumeStrategySelectedAssets.sumOf { it.activationCost }
        val totalCostForCostStrategy = costStrategySelectedAssets.sumOf { it.activationCost }
        val totalCostForRatioStrategy = ratioStrategySelectedAssets.sumOf { it.activationCost }

        println("TOTAL FOR VOLUME STRATEGY : $totalCostForVolumeStrategy")
        println("TOTAL FOR COST STRATEGY : $totalCostForCostStrategy")
        println("TOTAL FOR RATIO STRATEGY : $totalCostForRatioStrategy")

        assertTrue(totalCostForRatioStrategy <= totalCostForVolumeStrategy)
    }
}
package com.flexcity.assets.fcassets.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate

class AssetsSearchByCostPerVolumeTest {

    private val strategy = AssetsSearchByCostPerVolume()

    private val assets = listOf(
        Asset("A1", "Asset 1", 100.0, listOf(LocalDate.of(2026,3,10)), 50),
        Asset("A2", "Asset 2", 80.0, listOf(LocalDate.of(2026,3,10)), 30),
        Asset("A3", "Asset 3", 120.0, listOf(LocalDate.of(2026,3,11)), 60)
    )

    private val assetsByCode = assets.associateBy { it.code }

    @Test
    fun selectAssetsToCoverRequestedVolume() {
        val request = AssetRequest(LocalDate.of(2026, 3, 10), 30)
        val availableAssets = strategy.select(assets, request)

        assertTrue(availableAssets.isNotEmpty())
        val totalAvailableVolume = availableAssets.sumOf { it.volume }
        assertTrue(totalAvailableVolume >= request.volume)

        availableAssets.forEach { asset ->
            val originalAsset = requireNotNull(assetsByCode[asset.code])
            assertTrue(originalAsset.availability.contains(request.date))
            assertEquals(originalAsset.activationCost, asset.activationCost)
        }
    }

    @Test
    fun testExceptionIfRequestedVolumeNotCovered() {
        val request = AssetRequest(LocalDate.of(2026,3,10), 200)

        val exception = assertThrows(IllegalArgumentException::class.java) {
            strategy.select(assets, request)
        }

        assertEquals("The available assets volume is insufficient for the requested volume", exception.message)
    }

    @Test
    fun testIgnoreAssetsWithDifferentDates() {
        val request = AssetRequest(LocalDate.of(2026,3,11), 60)
        val selected = strategy.select(assets, request)

        assertEquals(1, selected.size)
        assertEquals("A3", selected[0].code)
    }

    @Test
    fun testCostFixedEvenIfPartialVolumeNeeded() {
        val request = AssetRequest(LocalDate.of(2026,3,10), 25)
        val selected = strategy.select(assets, request)

        selected.forEach { asset ->
            val original = requireNotNull(assetsByCode[asset.code])
            assertEquals(original.activationCost, asset.activationCost)
        }
    }
}
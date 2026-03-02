package com.flexcity.assets.fcassets.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate

class AssetsSearchByIncreasingVolumeTest {

    private val strategy = AssetsSearchByIncreasingVolume()

    private val assets = listOf(
        Asset("A1", "Asset 1", 100.0, listOf(LocalDate.of(2026,3,10)), 50),
        Asset("A2", "Asset 2", 80.0, listOf(LocalDate.of(2026,3,10)), 30),
        Asset("A3", "Asset 3", 120.0, listOf(LocalDate.of(2026,3,11)), 60)
    )

    @Test
    fun selectAssetsToCoverRequestedVolume() {
        val request = AssetRequest(LocalDate.of(2026, 3, 10), 60)
        val availableAssets = strategy.select(assets, request)

        val totalAvailableAssetsVolume = availableAssets.sumOf{ it.volume }
        assertTrue(totalAvailableAssetsVolume >= request.volume)

        availableAssets.forEach { asset ->
            val originalAsset = assets.find { it.code == asset.code }!!
            assertTrue { originalAsset.availability.contains(request.date) }
        }

        availableAssets.forEach { asset ->
            val originalAsset = assets.find { it.code == asset.code }!!
            assertEquals(originalAsset.activationCost, asset.activationCost)
        }
    }

    @Test
    fun testExceptionIfRequestedVolumeNotCovered() {
        val request = AssetRequest(LocalDate.of(2026,3,10), 200)

        val exception = assertThrows(IllegalArgumentException::class.java) {
            strategy.select(assets, request)
        }

        assertEquals("The volume available in assets are insufficient for requested volume", exception.message)
    }

}
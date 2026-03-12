package com.flexcity.assets.fcassets.domain

import com.flexcity.assets.fcassets.application.AssetSelectionService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDate

class AssetsSearchByIncreasingVolumeTest {

    private lateinit var assetSelectionService: AssetSelectionService
    private lateinit var assetStrategy: AssetsSearchByIncreasingVolume

    val asset1 = Asset(
        code = "A1",
        name = "Asset 1",
        activationCost = 100.0,
        volume = 50,
        availableDates = emptyList()
    )

    val asset2 = Asset(
        code = "A2",
        name = "Asset 2",
        activationCost = 80.0,
        volume = 30,
        availableDates = emptyList()
    )

    val asset3 = Asset(
        code = "A3",
        name = "Asset 3",
        activationCost = 120.0,
        volume = 60,
        availableDates = emptyList()
    )

    val availability1 = AssetAvailability(date = LocalDate.of(2026, 3, 10), asset = asset1)
    val availability2 = AssetAvailability(date = LocalDate.of(2026, 3, 10), asset = asset2)
    val availability3 = AssetAvailability(date = LocalDate.of(2026, 3, 11), asset = asset3)

    val assets = listOf(
        asset1.copy(availableDates = listOf(availability1)),
        asset2.copy(availableDates = listOf(availability2)),
        asset3.copy(availableDates = listOf(availability3))
    )

    private val assetsByCode = assets.associateBy { it.code }

    @BeforeEach
    fun setup() {
        assetSelectionService = AssetSelectionService()
        assetStrategy = AssetsSearchByIncreasingVolume(assetSelectionService)
    }
    @Test
    fun selectAssetsToCoverRequestedVolume() {
        val request = AssetRequest(LocalDate.of(2026, 3, 10), 30)
        val availableAssets = assetStrategy.select(assets, request)

        assertTrue(availableAssets.isNotEmpty())
        val totalAvailableVolume = availableAssets.sumOf { it.volume }
        assertTrue(totalAvailableVolume >= request.requestedVolume)

        availableAssets.forEach { asset ->
            val originalAsset = requireNotNull(assetsByCode[asset.code])
            val requestActivationDate: LocalDate = request.activationDate
            assertTrue(
                originalAsset.availableDates.any { it.date == requestActivationDate },
                "Asset needs to have the requested activation date"
            )
            assertEquals(originalAsset.activationCost, asset.activationCost)
        }
    }

    @Test
    fun testExceptionIfRequestedVolumeNotCovered() {
        val request = AssetRequest(LocalDate.of(2026,3,10), 200)

        val exception = assertThrows(IllegalArgumentException::class.java) {
            assetStrategy.select(assets, request)
        }

        assertEquals("The available assets volume is insufficient for the requested volume", exception.message)
    }

    @Test
    fun testIgnoreAssetsWithDifferentDates() {
        val request = AssetRequest(LocalDate.of(2026,3,11), 60)
        val selected = assetStrategy.select(assets, request)

        assertEquals(1, selected.size)
        assertEquals("A3", selected[0].code)
    }

    @Test
    fun testCostFixedEvenIfPartialVolumeNeeded() {
        val request = AssetRequest(LocalDate.of(2026,3,10), 25)
        val selected = assetStrategy.select(assets, request)

        selected.forEach { asset ->
            val original = requireNotNull(assetsByCode[asset.code])
            assertEquals(original.activationCost, asset.activationCost)
        }
    }

    @Test
    fun testModeSupportedIsEqual() {
        assertTrue(assetStrategy.modeSupported(CalculationMode.VOLUME))
    }

    @Test
    fun testModeSupportedIsDIfferent() {
        assertFalse(assetStrategy.modeSupported(CalculationMode.RATIO))
    }

}
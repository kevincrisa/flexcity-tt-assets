package com.flexcity.assets.fcassets.application

import com.flexcity.assets.fcassets.domain.Asset
import com.flexcity.assets.fcassets.domain.AssetAvailability
import com.flexcity.assets.fcassets.domain.AssetRequest
import com.flexcity.assets.fcassets.domain.AssetRequestStrategy
import com.flexcity.assets.fcassets.domain.AvailableAsset
import com.flexcity.assets.fcassets.domain.CalculationMode
import com.flexcity.assets.fcassets.infrastructure.AssetAvailabilityRepository
import com.flexcity.assets.fcassets.infrastructure.AssetRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDate

class AssetServiceTest {

    private lateinit var assetRepository: AssetRepository
    private lateinit var assetAvailabilityRepository: AssetAvailabilityRepository
    private lateinit var volumeStrategy: AssetRequestStrategy
    private lateinit var assetService: AssetService

    private lateinit var asset1: Asset
    private lateinit var asset2: Asset
    private lateinit var assets: List<Asset>

    /*val asset1 = Asset(
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
        volume = 40,
        availableDates = emptyList()
    )

    val availability1 = AssetAvailability(date = LocalDate.of(2026, 3, 10), asset = asset1)
    val availability2 = AssetAvailability(date = LocalDate.of(2026, 3, 10), asset = asset2)

    val asset1WithAvailability = asset1.copy(
        availableDates = listOf(availability1.copy(asset = asset1))
    )
    val asset2WithAvailability = asset2.copy(
        availableDates = listOf(availability2.copy(asset = asset2))
    )
    val assets = listOf(asset1WithAvailability, asset2WithAvailability)*/

    @BeforeEach
    fun setup() {
        assetRepository = mock(AssetRepository::class.java)
        assetAvailabilityRepository = mock(AssetAvailabilityRepository::class.java)

        asset1 = Asset(code = "A1", name = "Asset 1", activationCost = 100.0, volume = 50, availableDates = emptyList())
        asset2 = Asset(code = "A2", name = "Asset 2", activationCost = 80.0, volume = 40, availableDates = emptyList())

        val availability1 = AssetAvailability(id = 0, date = LocalDate.of(2026,3,10), asset = asset1)
        val availability2 = AssetAvailability(id = 0, date = LocalDate.of(2026,3,10), asset = asset2)

        asset1 = asset1.copy(availableDates = listOf(availability1))
        asset2 = asset2.copy(availableDates = listOf(availability2))

        assets = listOf(asset1, asset2)

        `when`(assetRepository.findAll()).thenReturn(assets)

        volumeStrategy = mock(AssetRequestStrategy::class.java)
        `when`(volumeStrategy.modeSupported(CalculationMode.VOLUME)).thenReturn(true)
        `when`(volumeStrategy.select(
            any<List<Asset>>() as List<Asset>,
            any<AssetRequest>() as AssetRequest
        )).thenReturn(
            listOf(
                AvailableAsset("A1", 50, 100.0),
                AvailableAsset("A2", 40, 80.0)
            )
        )

        assetService = AssetService(assetAvailabilityRepository, listOf(volumeStrategy))
    }

    @Test
    fun `getAvailableAssets should return assets using VOLUME strategy`() {
        val request = AssetRequest(LocalDate.of(2026,3,10), 50, CalculationMode.VOLUME)

        val result = assetService.retrieveAvailableAssets(request)

        assertEquals(2, result.size)
        assertEquals("A1", result[0].code)
        assertEquals("A2", result[1].code)
    }

    @Test
    fun `getAvailableAssets should throw exception for unsupported mode`() {
        val request = AssetRequest(LocalDate.of(2026,3,10), 50, CalculationMode.RATIO)

        val exception = assertThrows(IllegalArgumentException::class.java) {
            assetService.retrieveAvailableAssets(request)
        }

        assertEquals("No strategy found for mode ${request.mode}", exception.message)
    }
}
package com.flexcity.assets.fcassets.application

import com.flexcity.assets.fcassets.domain.Asset
import com.flexcity.assets.fcassets.domain.AssetRequest
import com.flexcity.assets.fcassets.domain.AssetRequestStrategy
import com.flexcity.assets.fcassets.domain.AvailableAsset
import com.flexcity.assets.fcassets.domain.CalculationMode
import com.flexcity.assets.fcassets.infrastructure.AssetRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.time.LocalDate

class AssetServiceTest {

    private lateinit var repository: AssetRepository
    private lateinit var volumeStrategy: AssetRequestStrategy
    private lateinit var service: AssetService

    private val assets = listOf(
        Asset("A1", "Asset 1", 100.0, listOf(LocalDate.of(2026, 3, 10)), 50),
        Asset("A2", "Asset 2", 80.0, listOf(LocalDate.of(2026,3,10)), 40)
    )

    @BeforeEach
    fun setup() {
        // Mock du repository
        repository = mock(AssetRepository::class.java)
        `when`(repository.getAllAssets()).thenReturn(assets)

        // Mock des stratégies
        volumeStrategy = mock(AssetRequestStrategy::class.java)
        `when`(volumeStrategy.modeSupported(CalculationMode.VOLUME)).thenReturn(true)
        `when`(volumeStrategy.select(assets, AssetRequest(LocalDate.of(2026, 3, 10), 50, CalculationMode.VOLUME)))
            .thenReturn(listOf(
                AvailableAsset("A1", 50, 100.0),
                AvailableAsset("A2", 40, 80.0))
            )

        service = AssetService(repository, listOf(volumeStrategy))
    }

    @Test
    fun `getAvailableAssets should return assets using VOLUME strategy`() {
        val request = AssetRequest(LocalDate.of(2026,3,10), 50, CalculationMode.VOLUME)
        val result = service.retrieveAvailableAssets(request)

        assertEquals(2, result.size)
        assertEquals("A1", result[0].code)
        assertEquals("A2", result[1].code)
    }

    @Test
    fun `getAvailableAssets should throw exception for unsupported mode`() {
        val request = AssetRequest(LocalDate.of(2026,3,10), 50, CalculationMode.RATIO)

        val exception = assertThrows(IllegalArgumentException::class.java) {
            service.retrieveAvailableAssets(request)
        }

        assertEquals("No strategy found for mode ${request.mode}", exception.message)
    }
}
package com.flexcity.assets.fcassets.infrastructure

import com.flexcity.assets.fcassets.domain.Asset
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import kotlin.test.assertNotNull

class AssetRepositoryTest {

    private val assetRepository: AssetRepository = mock(AssetRepository::class.java)

    @Test
    fun testGetAllAssets() {
        val assetsGenerated = (1..15).map { i ->
            Asset(
                code = "A$i",
                name = "Asset $i",
                activationCost = 100.0 - i,
                volume = 50 - i,
                availableDates = emptyList()
            )
        }

        `when`(assetRepository.findAll()).thenReturn(assetsGenerated)

        val assets: List<Asset> = assetRepository.findAll()

        assertNotNull(assets, "Assets list should not be null")
        assertEquals(15, assets.size)

        val firstAsset = assets.first()
        assertEquals("A1", firstAsset.code)
        assertEquals("Asset 1", firstAsset.name)
        assertEquals(100.0, firstAsset.activationCost)
        assertEquals(50, firstAsset.volume)

        val lastAsset = assets.last()
        assertEquals("A15", lastAsset.code)
        assertEquals("Asset 15", lastAsset.name)
        assertEquals(70.0, lastAsset.activationCost)
        assertEquals(35, lastAsset.volume)
    }
}
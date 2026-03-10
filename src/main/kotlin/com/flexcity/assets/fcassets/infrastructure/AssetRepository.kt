package com.flexcity.assets.fcassets.infrastructure

import com.flexcity.assets.fcassets.domain.Asset
import org.springframework.stereotype.Repository
import java.time.LocalDate

/**
 * Repository providing the assets.
 * Here, the data is in memory for demonstration purposes.
 */
@Repository
class AssetRepository {

    fun getAllAssets(): List<Asset> {
        return listOf(
            Asset("A1", "Asset 1", 100.0, listOf(LocalDate.of(2026,3,10), LocalDate.of(2026,3,11)), 50),
            Asset("A2", "Asset 2", 80.0, listOf(LocalDate.of(2026,3,10)), 40),
            Asset("A3", "Asset 3", 120.0, listOf(LocalDate.of(2026,3,12)), 60),
            Asset("A4", "Asset 4", 90.0, listOf(LocalDate.of(2026,3,11)), 35),
            Asset("A5", "Asset 5", 150.0, listOf(LocalDate.of(2026,3,10), LocalDate.of(2026,3,12)), 70),
            Asset("A6", "Asset 6", 60.0, listOf(LocalDate.of(2026,3,10)), 25),
            Asset("A7", "Asset 7", 110.0, listOf(LocalDate.of(2026,3,11)), 55),
            Asset("A8", "Asset 8", 95.0, listOf(LocalDate.of(2026,3,12)), 45),
            Asset("A9", "Asset 9", 75.0, listOf(LocalDate.of(2026,3,10)), 30),
            Asset("A10","Asset 10",130.0, listOf(LocalDate.of(2026,3,11), LocalDate.of(2026,3,12)), 65),
            Asset("A11","Asset 11",85.0, listOf(LocalDate.of(2026,3,10)), 20),
            Asset("A12","Asset 12",140.0, listOf(LocalDate.of(2026,3,12)), 60),
            Asset("A13","Asset 13",55.0, listOf(LocalDate.of(2026,3,11)), 15),
            Asset("A14","Asset 14",160.0, listOf(LocalDate.of(2026,3,10)), 75),
            Asset("A15","Asset 15",70.0, listOf(LocalDate.of(2026,3,12)), 35)
        )
    }

}
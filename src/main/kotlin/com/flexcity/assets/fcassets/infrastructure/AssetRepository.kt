package com.flexcity.assets.fcassets.infrastructure

import com.flexcity.assets.fcassets.domain.Asset
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repository providing the assets.
 * Here, the data is in memory for demonstration purposes.
 */
@Repository
interface AssetRepository : JpaRepository<Asset, String> {
}
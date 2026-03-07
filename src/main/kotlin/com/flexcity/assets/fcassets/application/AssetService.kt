package com.flexcity.assets.fcassets.application

import com.flexcity.assets.fcassets.domain.*
import com.flexcity.assets.fcassets.infrastructure.AssetRepository
import org.springframework.stereotype.Service

/**
 * Service responsible for the business logic related to asset selection.
 *
 * It retrieves all assets from the repository and delegates the selection
 * logic to the appropriate strategy depending on the request mode.
 */
@Service
class AssetService (
    private val assetRepository: AssetRepository,
    private val strategies: List<AssetRequestStrategy>
){
    /**
     * Returns the list of available assets based on the request.
     */
    fun getAvailableAssets(request: AssetRequest): List<AvailableAsset>{
        // Retrieve all assets from the repository
        val assets = assetRepository.getAllAssets()

        /**
         * Find the strategy that supports the requested mode.
         *
         * Modes accepted are:
         * - VOLUME
         * - COST
         * - RATIO
         */
        val strategy = strategies.firstOrNull {
            it.modeSupported(request.mode)
        } ?: throw IllegalArgumentException("No strategy found for mode ${request.mode}")

        // Return the list of available assets based on all assets retrieved on repository and request data
        return strategy.select(assets, request)
    }
}
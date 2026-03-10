package com.flexcity.assets.fcassets.application

import com.flexcity.assets.fcassets.domain.*
import com.flexcity.assets.fcassets.infrastructure.AssetRepository
import org.springframework.stereotype.Service

@Service
class AssetService (
    private val assetRepository: AssetRepository,
    private val assetRequestStrategies: List<AssetRequestStrategy>
){

    fun retrieveAvailableAssets(assetRequest: AssetRequest): List<AvailableAsset>{
        // Retrieve all assets from the repository
        val allAssets = assetRepository.getAllAssets()

        val strategy = assetRequestStrategies.firstOrNull {
            it.modeSupported(assetRequest.mode)
        } ?: throw IllegalArgumentException("No strategy found for mode ${assetRequest.mode}")

        return strategy.select(allAssets, assetRequest)
    }
}
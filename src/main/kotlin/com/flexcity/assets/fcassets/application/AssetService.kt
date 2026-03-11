package com.flexcity.assets.fcassets.application

import com.flexcity.assets.fcassets.domain.*
import com.flexcity.assets.fcassets.infrastructure.AssetAvailabilityRepository
import org.springframework.stereotype.Service

@Service
class AssetService (
    private val assetAvailabilityRepository: AssetAvailabilityRepository,
    private val assetRequestStrategies: List<AssetRequestStrategy>
){

    fun retrieveAvailableAssets(assetRequest: AssetRequest): List<AvailableAsset>{
        val availabilities = assetAvailabilityRepository.findByDate(assetRequest.activationDate)

        val assetsForStrategy = availabilities.map { it.asset }

        val strategy = assetRequestStrategies.firstOrNull {
            it.modeSupported(assetRequest.mode)
        } ?: throw IllegalArgumentException("No strategy found for mode ${assetRequest.mode}")

        return strategy.select(assetsForStrategy, assetRequest)
    }
}
package com.flexcity.assets.fcassets.domain

import org.springframework.stereotype.Component

@Component
class AssetsSearchByCostPerVolume: AssetRequestStrategy {

    override fun modeSupported(mode: CalculationMode) =
        mode == CalculationMode.RATIO

    override fun select(
        assets: List<Asset>,
        request: AssetRequest
    ): List<AvailableAsset> {
        val availableAssetsAtDate = assets.filter { it.availability.contains(request.date) }
        if (availableAssetsAtDate.isEmpty()) return emptyList()

        val availableAssetsSortByCostPerVolume =
            availableAssetsAtDate.sortedBy { it.activationCost / it.volume.toDouble() }

        val assetsSelected = mutableListOf<AvailableAsset>()
        var requestedVolume = request.volume

        for (asset in availableAssetsSortByCostPerVolume){
            if(requestedVolume <= 0) break
            assetsSelected.add(AvailableAsset(asset.code, asset.volume, asset.activationCost))
            requestedVolume -= asset.volume
        }

        if (requestedVolume > 0){
            throw IllegalArgumentException("The volume available in assets are insufficient for requested volume")
        }

        return assetsSelected    }
}
package com.flexcity.assets.fcassets.domain

import org.springframework.stereotype.Component

@Component
class AssetsSearchByIncreasingCost: AssetRequestStrategy {

    override fun modeSupported(mode: CalculationMode) =
        mode == CalculationMode.COST

    override fun select(
        assets: List<Asset>,
        request: AssetRequest
    ): List<AvailableAsset> {
        val availableAssetsAtDate = assets.filter { it.availability.contains(request.date) }
        if (availableAssetsAtDate.isEmpty()) return emptyList()

        val availableAssetsSortByIncreasingCost = availableAssetsAtDate.sortedBy { it.activationCost }

        val assetsSelected = mutableListOf<AvailableAsset>()
        var requestedVolume = request.volume

        for (asset in availableAssetsSortByIncreasingCost){
            if(requestedVolume <= 0) break
            val availableVolume = minOf(asset.volume, requestedVolume)
            assetsSelected.add(AvailableAsset(asset.code, availableVolume, asset.activationCost))
            requestedVolume -= availableVolume
        }

        if (requestedVolume > 0){
            throw IllegalArgumentException("The volume available in assets are insufficient for requested volume")
        }

        return assetsSelected
    }
}
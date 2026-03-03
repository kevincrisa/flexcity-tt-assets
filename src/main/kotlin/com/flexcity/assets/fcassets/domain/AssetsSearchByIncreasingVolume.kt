package com.flexcity.assets.fcassets.domain

// Search list of available assets sorted by increasing volume
class AssetsSearchByIncreasingVolume: AssetRequestStrategy {

    override fun modeSupported(mode: CalculationMode) =
        mode == CalculationMode.VOLUME

    override fun select(
        assets: List<Asset>,
        request: AssetRequest
    ): List<AvailableAsset> {
        val availableAssetsAtDate = assets.filter { it.availability.contains(request.date) }
        if (availableAssetsAtDate.isEmpty()) return emptyList()

        val availableAssetsSortByIncreasingVolume = availableAssetsAtDate.sortedBy { it.volume }

        val assetsSelected = mutableListOf<AvailableAsset>()
        var requestedVolume = request.volume

        for (asset in availableAssetsSortByIncreasingVolume){
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
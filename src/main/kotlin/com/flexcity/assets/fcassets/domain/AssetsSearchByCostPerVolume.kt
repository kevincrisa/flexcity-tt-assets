package com.flexcity.assets.fcassets.domain

import org.springframework.stereotype.Component

/**
 * Strategy used to select assets based on the cost per volume calculation
 *
 * Assets are sorted by their activation cost divided by their volume.
 * The algorithm then selects assets until the requested volume is satisfied.
 */
@Component
class AssetsSearchByCostPerVolume: AssetRequestStrategy {

    /**
     * Indicates that this strategy supports the RATIO calculation mode.
     *
     * @param calculationMode Mode sent by the request
     * @return Return if the mode sent by the request is equal to "RATIO" calculation mode
     */
    override fun modeSupported(calculationMode: CalculationMode) =
        calculationMode == CalculationMode.RATIO

    /**
     * Selects assets according to the cost-per-volume ratio.
     *
     * Steps:
     * 1. Filter assets that are available on the requested date
     * 2. Sort them by cost per volume (ascending)
     * 3. Select assets until the requested volume is reached
     *
     * @param assetsList List of all assets
     * @param assetRequest AssetRequest which represents the request body sent by the client
     * @return List of selected assets by the cost-per-volume calculation mode
     */
    override fun select(
        assetsList: List<Asset>,
        assetRequest: AssetRequest
    ): List<AvailableAsset> {
        // Filter all assets to select only available at requested date
        val availableAssetsAtDate = assetsList.filter { it.availability.contains(assetRequest.date) }
        // If no assets are available at requested date, return empty list
        if (availableAssetsAtDate.isEmpty()) return emptyList()

        // Sort assets by cost-per-volume
        val availableAssetsSortByCostPerVolume =
            availableAssetsAtDate.sortedBy { it.activationCost / it.volume.toDouble() }

        // List which will contain assets selected
        val assetsSelected = mutableListOf<AvailableAsset>()
        // Requested volume will be used for check if we need to fill the assets selected list
        var requestedVolume = assetRequest.volume

        // Iteration in available assets to fill the list
        for (asset in availableAssetsSortByCostPerVolume){
            // If the request volume has been satisfied, stop the loop
            if(requestedVolume <= 0) break
            // Add current asset in the assets selected list
            assetsSelected.add(AvailableAsset(asset.code, asset.volume, asset.activationCost))
            // Decrease the asset volume to requested volume
            requestedVolume -= asset.volume
        }

        // If after selecting all possible assets we still need volume,
        // it means the request cannot be satisfied
        if (requestedVolume > 0){
            throw IllegalArgumentException("The volume available in assets are insufficient for requested volume")
        }

        // Return list of assets selected
        return assetsSelected
    }
}
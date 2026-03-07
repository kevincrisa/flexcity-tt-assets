package com.flexcity.assets.fcassets.domain

import org.springframework.stereotype.Component

/**
 * Strategy used to select assets based on increasing cost sort
 *
 * Assets are sorted by their activation cost.
 * The algorithm then selects assets until the requested volume is satisfied.
 */
@Component
class AssetsSearchByIncreasingCost: AssetRequestStrategy {

    /**
     * Indicates that this strategy supports the COST calculation mode.
     *
     * @param calculationMode Mode sent by the request
     * @return Return if the mode sent by the request is equal to "COST" calculation mode
     */
    override fun modeSupported(calculationMode: CalculationMode) =
        calculationMode == CalculationMode.COST

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

        // Sort assets by cost
        val availableAssetsSortByIncreasingCost = availableAssetsAtDate.sortedBy { it.activationCost }

        // Use helper to select assets and validate volume
        return AssetsSearchHelper.selectAssetsToFillVolume(
            availableAssetsSortByIncreasingCost, assetRequest.volume)
    }
}
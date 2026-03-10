package com.flexcity.assets.fcassets.domain

import org.springframework.stereotype.Component

/**
 * Select the assets by sorting them by increasing cost and entering the requested volume.
 */
@Component
class AssetsSearchByIncreasingCost: AssetRequestStrategy {

    override fun modeSupported(calculationMode: CalculationMode) =
        calculationMode == CalculationMode.COST

    override fun select(
        assetsList: List<Asset>,
        assetRequest: AssetRequest
    ): List<AvailableAsset> {
        val availableAssets = assetsList.filter { it.availableDates.contains(assetRequest.activationDate) }
        if (availableAssets.isEmpty()) return emptyList()

        val assetsSortedByCost = availableAssets.sortedBy { it.activationCost }

        return AssetsSearchHelper.selectAssetsToFillVolume(
            assetsSortedByCost, assetRequest.requestedVolume)
    }
}